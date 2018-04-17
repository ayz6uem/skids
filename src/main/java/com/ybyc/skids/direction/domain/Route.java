package com.ybyc.skids.direction.domain;

import com.ybyc.skids.common.helper.TimeHelper;
import com.ybyc.skids.core.Car;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Data
public class Route {

    private long datetime;
    private int current = 0;
    private List<Step> steps = new ArrayList<>();

    /**
     * @param car
     * @return false 未完成，true 已完成
     */
    public boolean next(Car car) {
        if (hasStep()) {
            Step step = findStep();
            if (Objects.nonNull(step)) {
                return move(car, step);
            }
            return false;
        } else {
            return finishMove(car);
        }
    }

    public boolean finishMove(Car car) {
        car.getStatus().setSpeed(0);
        car.setInstruction("");
        log.info("car finish:{}",car.getCarNumber());
        return true;
    }

    public boolean move(Car car, Step step) {
        car.getStatus().setSpeed(step.getSpeed());
        car.getStatus().incOdo(step.getDistance());
        car.getStatus().decSoc(step.getDistance() / 1.5);
        car.getStatus().setLongitude(step.getLongitude());
        car.getStatus().setLatitude(step.getLatitude());
        car.setInstruction(step.getInstruction());
        datetime = TimeHelper.currentSeconds();
        log.info("car move:{} {}",car.getCarNumber(),step);
        return false;
    }

    private boolean hasStep() {
        return current+1 < steps.size();
    }

    private Step findStep() {
        double offsetDistance = 0;
        if (datetime > 0) {
            long seconds = TimeHelper.currentSeconds() - datetime;
            long d = 0;
            long lastOffset;
            long offset = 0;

            do{
                Step step = steps.get(++current);
                d += step.getDuration();
                lastOffset = offset;
                offset = Math.abs(d - seconds);
                offsetDistance += step.getDistance();
            }while (d < seconds && current+1 < steps.size());

            if(lastOffset < offset){
                current --;
                offsetDistance -= steps.get(current).getDistance();
            }
        }
        Step step = steps.get(current);
        step.setDistance(offsetDistance + step.getDistance());
        return step;
    }

}
