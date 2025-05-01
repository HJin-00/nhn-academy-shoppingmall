package com.nhnacademy.shoppingmall.user.domain;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Point {
    @Getter
    public enum PointDescription{
        EARN("적립"),
        USE("사용");

        private final String description;
        PointDescription(String description){
            this.description = description;
        }
        public static PointDescription from(String description){
            for(PointDescription pointDescription : values()){
                if(pointDescription.description.equals(description)){
                    return pointDescription;
                }
            }
            throw new IllegalArgumentException(description);
        }


    }
    private int pointHistoryId;
    private String userId;
    private int changePoint;
    private PointDescription pointDescription;
    private LocalDateTime createdAt;

    public Point(String userId, int changePoint, PointDescription pointDescription , LocalDateTime createdAt) {
        this.userId = userId;
        this.changePoint = changePoint;
        this.pointDescription = pointDescription;
        this.createdAt = createdAt;
    }

    public Point(){

    }

    @Override
    public String toString() {
        return "Point{" +
                "userId=" + userId +
                ", changePoint=" + changePoint +
                ", pointDescription=" + pointDescription.getDescription() +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

}
