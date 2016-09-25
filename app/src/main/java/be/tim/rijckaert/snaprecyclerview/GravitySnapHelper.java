package be.tim.rijckaert.snaprecyclerview;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.View;

/**
 * Created by tim on 25.09.16.
 */
public class GravitySnapHelper extends SnapHelper {

    private static final float INVALID_DISTANCE = 1f;
    private static final float VIEW_HALF_VISIBLE = 0.5f;

    private final int gravity;
    private OrientationHelper verticalHelper;
    private OrientationHelper horizontalHelper;

    public GravitySnapHelper(final int gravity) {
        this.gravity = gravity;
    }

    @Override
    public int findTargetSnapPosition(final LayoutManager layoutManager, final int velocityX, final int velocityY) {
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return RecyclerView.NO_POSITION;
        }

        final int itemCount = layoutManager.getItemCount();
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION;
        }

        final View currentView = findSnapView(layoutManager);
        if (currentView == null) {
            return RecyclerView.NO_POSITION;
        }

        final int currentPosition = layoutManager.getPosition(currentView);
        if (currentPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        }

        RecyclerView.SmoothScroller.ScrollVectorProvider vectorProvider =
                (RecyclerView.SmoothScroller.ScrollVectorProvider) layoutManager;
        // deltaJumps sign comes from the velocity which may not match the order of children in
        // the LayoutManager. To overcome this, we ask for a vector from the LayoutManager to
        // get the direction.
        PointF vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1);
        if (vectorForEnd == null) {
            // cannot get a vector for the given position.
            return RecyclerView.NO_POSITION;
        }

        int vDeltaJump, hDeltaJump;
        if (layoutManager.canScrollHorizontally()) {
            hDeltaJump = estimateNextPositionDiffForFling(layoutManager,
                    getHorizontalHelper(layoutManager), velocityX, 0);
            if (vectorForEnd.x < 0) {
                hDeltaJump = -hDeltaJump;
            }
        } else {
            hDeltaJump = 0;
        }
        if (layoutManager.canScrollVertically()) {
            vDeltaJump = estimateNextPositionDiffForFling(layoutManager,
                    getVerticalHelper(layoutManager), 0, velocityY);
            if (vectorForEnd.y < 0) {
                vDeltaJump = -vDeltaJump;
            }
        } else {
            vDeltaJump = 0;
        }

        int deltaJump = layoutManager.canScrollVertically() ? vDeltaJump : hDeltaJump;
        if (deltaJump == 0) {
            return RecyclerView.NO_POSITION;
        }

        int targetPos = currentPosition + deltaJump;
        if (targetPos < 0) {
            targetPos = 0;
        }
        if (targetPos >= itemCount) {
            targetPos = itemCount - 1;
        }
        return targetPos;
    }

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull final LayoutManager layoutManager, @NonNull final View targetView) {
        int[] out = new int[2];
        out[0] = layoutManager.canScrollHorizontally() ? getXCoordinateToSnapPos(layoutManager, targetView) : 0;
        out[1] = layoutManager.canScrollVertically() ? getYCoordinateToSnapPos(layoutManager, targetView) : 0;
        return out;
    }

    @Nullable
    @Override
    public View findSnapView(final LayoutManager layoutManager) {
        View targetView = null;
        if (layoutManager instanceof LinearLayoutManager) {
            switch (gravity) {
                case Gravity.START:
                    targetView = findStartView(layoutManager, getHorizontalHelper(layoutManager));
                    break;
                case Gravity.END:
                    targetView = findEndView(layoutManager, getHorizontalHelper(layoutManager));
                    break;
                case Gravity.TOP:
                    targetView = findStartView(layoutManager, getVerticalHelper(layoutManager));
                    break;
                case Gravity.BOTTOM:
                    targetView = findEndView(layoutManager, getVerticalHelper(layoutManager));
                    break;
            }
        }
        return targetView;
    }

    //<editor-fold desc="Helpers">

    //<editor-fold desc="Coordinate Helpers">
    private int getXCoordinateToSnapPos(final LayoutManager layoutManager, final View targetView) {
        final OrientationHelper horizontalHelper = getHorizontalHelper(layoutManager);

        return (gravity == Gravity.START) ?
                horizontalHelper.getDecoratedStart(targetView) - horizontalHelper.getStartAfterPadding() :
                horizontalHelper.getDecoratedEnd(targetView) - horizontalHelper.getEndAfterPadding();
    }

    private int getYCoordinateToSnapPos(final LayoutManager layoutManager, final View targetView) {
        final OrientationHelper verticalHelper = getVerticalHelper(layoutManager);

        return (gravity == Gravity.TOP) ?
                verticalHelper.getDecoratedStart(targetView) - verticalHelper.getStartAfterPadding() :
                verticalHelper.getDecoratedEnd(targetView) - verticalHelper.getEndAfterPadding();
    }
    //</editor-fold>

    //<editor-fold desc="OrientationHelper">
    private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        if (verticalHelper == null) {
            verticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return verticalHelper;
    }

    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        if (horizontalHelper == null) {
            horizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return horizontalHelper;
    }
    //</editor-fold>

    //<editor-fold desc="View finders">
    private View findEndView(final LayoutManager layoutManager, final OrientationHelper orientationHelper) {
        View targetView = null;
        if (layoutManager instanceof LinearLayoutManager) {
            final int lastChildPos = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            if (lastChildPos != RecyclerView.NO_POSITION) {
                final View lastView = layoutManager.findViewByPosition(lastChildPos);
                float visibleWidth = (float) orientationHelper.getTotalSpace() - orientationHelper.getDecoratedStart(lastView) / orientationHelper.getDecoratedMeasurement(lastView);

                if (visibleWidth > VIEW_HALF_VISIBLE) {
                    targetView = lastView;
                } else {
                    // If we're at the start of the list, we shouldn't snap
                    // to avoid having the first item not completely visible.
                    boolean startOfList = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition() == 0;
                    if (!startOfList) {
                        targetView = layoutManager.findViewByPosition(lastChildPos - 1);
                    }
                }
            }
        }
        return targetView;
    }

    private View findStartView(final LayoutManager layoutManager, final OrientationHelper orientationHelper) {
        View targetView = null;
        if (layoutManager instanceof LinearLayoutManager) {
            final int firstChildPos = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            if (firstChildPos != RecyclerView.NO_POSITION) {
                final View firstView = layoutManager.findViewByPosition(firstChildPos);
                float visibleWidth = (float) orientationHelper.getDecoratedEnd(firstView) / orientationHelper.getDecoratedMeasurement(firstView);

                if (visibleWidth > VIEW_HALF_VISIBLE) {
                    targetView = firstView;
                } else {
                    // If we're at the end of the list, we shouldn't snap
                    // to avoid having the last item not completely visible.
                    boolean endOfList = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1;
                    if (!endOfList) {
                        // If the firstView wasn't returned, we need to return
                        // the next view closest to the start.
                        targetView = layoutManager.findViewByPosition(firstChildPos + 1);
                    }
                }
            }
        }
        return targetView;
    }
    //</editor-fold>

    //<editor-fold desc="Fail Inheritance COPIED FROM LinearSnapHelper">
    private int estimateNextPositionDiffForFling(RecyclerView.LayoutManager layoutManager,
                                                 OrientationHelper helper, int velocityX, int velocityY) {
        int[] distances = calculateScrollDistance(velocityX, velocityY);
        float distancePerChild = computeDistancePerChild(layoutManager, helper);
        if (distancePerChild <= 0) {
            return 0;
        }
        int distance =
                Math.abs(distances[0]) > Math.abs(distances[1]) ? distances[0] : distances[1];

        if (Math.abs(distance) < distancePerChild / 2f) {
            return 0;
        }

        return (int) Math.floor(distance / distancePerChild);
    }

    private float computeDistancePerChild(RecyclerView.LayoutManager layoutManager,
                                          OrientationHelper helper) {
        View minPosView = null;
        View maxPosView = null;
        int minPos = Integer.MAX_VALUE;
        int maxPos = Integer.MIN_VALUE;
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return INVALID_DISTANCE;
        }

        for (int i = 0; i < childCount; i++) {
            View child = layoutManager.getChildAt(i);
            final int pos = layoutManager.getPosition(child);
            if (pos == RecyclerView.NO_POSITION) {
                continue;
            }
            if (pos < minPos) {
                minPos = pos;
                minPosView = child;
            }
            if (pos > maxPos) {
                maxPos = pos;
                maxPosView = child;
            }
        }
        if (minPosView == null || maxPosView == null) {
            return INVALID_DISTANCE;
        }
        int start = Math.min(helper.getDecoratedStart(minPosView),
                helper.getDecoratedStart(maxPosView));
        int end = Math.max(helper.getDecoratedEnd(minPosView),
                helper.getDecoratedEnd(maxPosView));
        int distance = end - start;
        if (distance == 0) {
            return INVALID_DISTANCE;
        }
        return 1f * distance / ((maxPos - minPos) + 1);
    }
    //</editor-fold>

    //</editor-fold>
}