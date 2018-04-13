package com.ybyc.skids.direction.dto;

import com.ybyc.skids.direction.domain.Route;
import com.ybyc.skids.direction.domain.Step;
import lombok.Data;

import java.util.List;

@Data
public class DrivingDTO {

    private int status;
    private String info;
    private int infocode;
    private int count;
    private RouteDTO route;

    public boolean ok(){
        return status == 1;
    }

    @Data
    public static class RouteDTO{

        private String origin;
        private String destination;
        private List<PathDTO> paths;

        public PathDTO getPath(){
            if(paths!=null && paths.size()>=1){
                return paths.get(0);
            }
            return null;
        }

    }

    @Data
    public static class PathDTO{

        private int distance;
        private int duration;
        private String strategy;

        private List<StepDTO> steps;

    }

    @Data
    public static class StepDTO{

        private String instruction;
        private int distance;
        private int duration;

        private String polyline;

    }

    public Route parseRoute(){
        Route route = new Route();
        PathDTO pathDTO = getRoute().getPath();
        List<StepDTO> stepDTOS = pathDTO.getSteps();
        stepDTOS.forEach(stepDTO -> {

            String[] points = stepDTO.polyline.split(";");
            double preDistance = ((int)Math.ceil(stepDTO.distance * 1.0 / points.length))*0.001;
            int preDuration = (int)Math.ceil(stepDTO.duration * 1.0 / points.length);
            double speed = ((int)(stepDTO.distance * 1.0 / stepDTO.duration * 3.6 * 100))/100.0;
            for (String point : points) {
                String[] location = point.split(",");
                double longitude = Double.parseDouble(location[0]);
                double latitude = Double.parseDouble(location[1]);

                Step step = new Step();
                step.setDistance(preDistance);
                step.setDuration(preDuration);
                step.setInstruction(stepDTO.instruction);
                step.setLongitude(longitude);
                step.setLatitude(latitude);
                step.setSpeed(speed);

                route.getSteps().add(step);

            }
        });
        return route;
    }

}
