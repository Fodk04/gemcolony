package com.fodk.gemcolony.entity.custom.gem.ai;


import com.fodk.gemcolony.data.FusionData;
import com.fodk.gemcolony.data.FusionRegistryData;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;
import java.util.*;
import java.util.logging.Logger;

public class GemFuseGoal extends Goal {

    private static final Logger LOGGER = Logger.getLogger(GemFuseGoal.class.getName());

    private final GemEntity gem;
    private final double speed;

    private UUID fusionId;
    private int repathCooldown;

    public GemFuseGoal(GemEntity gem, double speed) {
        this.gem = gem;
        this.speed = speed;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!gem.isFusing() || !(gem.level() instanceof ServerLevel level)) {
            return false;
        }

        FusionRegistryData registry = FusionRegistryData.get(level);
        FusionData session = registry.getSessionForGem(gem.getUUID()).orElse(null);

        if (session == null) {
            gem.setFusing(false);
            return false;
        }

        if (session.state() != FusionData.FusionState.GATHERING && session.state() != FusionData.FusionState.READY) {
            return false;
        }

        fusionId = session.fusionId();
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (!gem.isFusing() || !(gem.level() instanceof ServerLevel level) || fusionId == null) {
            return false;
        }

        FusionData session = FusionRegistryData.get(level).getSession(fusionId).orElse(null);

        return session != null && (session.state() == FusionData.FusionState.GATHERING
                || session.state() == FusionData.FusionState.READY)
                && session.containsParticipant(gem.getUUID());
    }

    @Override
    public void start() {
        repathCooldown = 0;
    }

    @Override
    public void tick() {
        if (!(gem.level() instanceof ServerLevel level) || fusionId == null) {
            return;
        }

        FusionRegistryData registry = FusionRegistryData.get(level);
        FusionData session = registry.getSession(fusionId).orElse(null);

        if (session == null || (session.state() != FusionData.FusionState.GATHERING && session.state() != FusionData.FusionState.READY)) {
            gem.getNavigation().stop();
            return;
        }

        List<GemEntity> participants = new ArrayList<>();

        for (UUID uuid : session.participants()) {
            Entity entity = level.getEntity(uuid);

            if (!(entity instanceof GemEntity participant) || !participant.isAlive()) {
                registry.cancelSession(fusionId, level);
                gem.getNavigation().stop();
                return;
            }

            participants.add(participant);
        }

        if (participants.size() < 2) {
            gem.getNavigation().stop();
            return;
        }

        // Update READY status according to actual bounding-box contact.
        for (GemEntity participant : participants) {
            if (isTouchingAnyParticipant(participant, participants)) {
                registry.markParticipantReady(fusionId, participant.getUUID());
            } else {
                registry.clearParticipantReady(fusionId, participant.getUUID());
            }
        }

        session = registry.getSession(fusionId).orElse(null);

        if (session == null) {
            gem.getNavigation().stop();
            return;
        }

        // Fuse only when everyone is ready and all participants
        // belong to one connected cluster.
        if (session.areAllParticipantsReady() && isConnectedCluster(participants)) {gem.getNavigation().stop();

            boolean becameReady = registry.markReadyIfEveryoneReady(fusionId);

            if (becameReady || session.state() == FusionData.FusionState.READY) {
                gem.completeSameGemFusion(level, fusionId);
            }

            return;
        }

        // The first participant is the stationary anchor.
        UUID anchorUUID = session.participants().get(0);

        if (gem.getUUID().equals(anchorUUID)) {
            gem.getNavigation().stop();
            return;
        }

        GemEntity anchor = participants.stream()
                .filter(participant -> participant.getUUID().equals(anchorUUID))
                .findFirst()
                .orElse(null);

        if (anchor == null) {
            gem.getNavigation().stop();
            return;
        }

        // Every non-anchor gem keeps approaching the anchor,
        // even when already touching another participant.
        if (repathCooldown > 0) {
            repathCooldown--;
        }

        if (repathCooldown <= 0 || (gem.getNavigation().isDone() && !isTouchingAnyParticipant(gem, participants))) {
            Path path = gem.getNavigation().createPath(anchor, 0);

            boolean started = path != null && path.canReach() && gem.getNavigation().moveTo(path, speed);

            repathCooldown = started ? 10 : 5;

            if (!started && path == null) {
                LOGGER.fine("[Gem Colony Fusion] No path to anchor: " + gem.getUUID());
            }
        }
    }

    private boolean isTouchingAnyParticipant(GemEntity source, List<GemEntity> participants) {
        for (GemEntity other : participants) {
            if (other != source && isTouching(source, other)) {
                return true;
            }
        }
        return false;
    }


    private double getContactLeniency(GemEntity gem) {
        return Mth.clamp(0.3D * (0.3D / gem.getBbWidth()), 0.02D, 0.3D);
    }

    private boolean isTouching(GemEntity first, GemEntity second) {
        return first.getBoundingBox()
                .inflate(getContactLeniency(first))
                .intersects(second.getBoundingBox().inflate(getContactLeniency(second)));
    }

    private boolean isConnectedCluster(List<GemEntity> participants) {
        if (participants.isEmpty()) {
            return false;
        }

        Set<UUID> visited = new HashSet<>();
        Deque<GemEntity> queue = new ArrayDeque<>();

        queue.add(participants.get(0));
        visited.add(participants.get(0).getUUID());

        while (!queue.isEmpty()) {
            GemEntity current = queue.removeFirst();

            for (GemEntity other : participants) {
                if (visited.contains(other.getUUID())) {
                    continue;
                }

                if (isTouching(current, other)) {
                    visited.add(other.getUUID());
                    queue.addLast(other);
                }
            }
        }

        return visited.size() == participants.size();
    }

    @Override
    public void stop() {
        gem.getNavigation().stop();
        fusionId = null;
        repathCooldown = 0;
    }
}