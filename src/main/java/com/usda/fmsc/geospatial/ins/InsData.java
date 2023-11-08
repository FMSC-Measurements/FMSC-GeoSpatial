package com.usda.fmsc.geospatial.ins;

public class InsData implements IINSData {
    final boolean isConsecutive;
    final double timeSpan;
    final long timeSinceStart;
    final double distX, distY, distZ;
    final double linAccelX, linAccelY, linAccelZ;
    final double accelX, accelY, accelZ;
    final double velX, velY, velZ;
    final double rotX, rotY, rotZ;
    final double yaw, pitch, roll;


    public InsData(boolean isConsecutive, double timeSpan, long timeSinceStart,
            double distX, double distY, double distZ,
            double linAccelX, double linAccelY, double linAccelZ,
            double accelX, double accelY, double accelZ,
            double velX, double velY, double velZ,
            double rotX, double rotY, double rotZ,
            double yaw, double pitch, double roll) {
        
        this.isConsecutive = isConsecutive;
        this.timeSpan = timeSpan;
        this.timeSinceStart = timeSinceStart;

        this.distX = distX;
        this.distY = distY;
        this.distZ = distZ;

        this.linAccelX = linAccelX;
        this.linAccelY = linAccelY;
        this.linAccelZ = linAccelZ;

        this.accelX = accelX;
        this.accelY = accelY;
        this.accelZ = accelZ;

        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;

        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;

        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
    }


    @Override
    public double getDistanceX() {
        return distX;
    }

    @Override
    public double getDistanceY() {
        return distY;
    }

    @Override
    public double getDistanceZ() {
        return distZ;
    }

    @Override
    public double getTimeSpan() {
        return timeSpan;
    }

    @Override
    public double getLinearAccelX() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLinearAccelX'");
    }


    @Override
    public double getLinearAccelY() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLinearAccelY'");
    }


    @Override
    public double getLinearAccelZ() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLinearAccelZ'");
    }

    @Override
    public double getAccelX() {
        return accelX;
    }

    @Override
    public double getAccelY() {
        return accelY;
    }

    @Override
    public double getAccelZ() {
        return accelZ;
    }

    @Override
    public double getVelocityX() {
        return velX;
    }

    @Override
    public double getVelocityY() {
        return velY;
    }

    @Override
    public double getVelocityZ() {
        return velZ;
    }

    @Override
    public double getYaw() {
        return yaw;
    }

    @Override
    public double getPitch() {
        return pitch;
    }

    @Override
    public double getRoll() {
        return roll;
    }

    @Override
    public double getRotationX() {
        return rotX;
    }

    @Override
    public double getRotationY() {
        return rotY;
    }

    @Override
    public double getRotationZ() {
        return rotZ;
    }

    @Override
    public long getTimeSinceStart() {
        return timeSinceStart;
    }

    @Override
    public boolean isConsecutive() {
        return isConsecutive;
    }

    @Override
    public String toString() {
        return String.format("[INS] Dist: T%f (%f : %f : %f) | Vel: (%f : %f : %f) |  Rot: (%f : %f : %f) | YPR: (%f : %f : %f)%s",
                getTimeSpan(),
                getDistanceX(), getDistanceY(), getDistanceZ(),
                getVelocityX(), getVelocityY(), getVelocityZ(),
                getRotationX(), getRotationY(), getRotationZ(),
                getYaw(), getPitch(), getRoll(),
                isConsecutive ? "" : " [Not Consecutive]");
    }


}
