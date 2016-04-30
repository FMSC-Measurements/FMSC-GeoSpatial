package com.usda.fmsc.geospatial;

public class Units {
    public enum NorthSouth {
        North(0),
        South(1);

        private final int value;

        NorthSouth(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static NorthSouth parse(int id) {
            NorthSouth[] types = values();
            if(types.length > id && id > -1)
                return types[id];
            throw new IllegalArgumentException("Invalid NorthSouth id: " + id);
        }

        public static NorthSouth parse(String value) {
            if (value == null) {
                throw new NullPointerException();
            }

            switch(value.toLowerCase()) {
                case "s":
                case "south": return South;
                case "n":
                case "north": return North;
                default: throw new IllegalArgumentException(String.format("Invalid NorthSouth Name: %s", value));
            }
        }

        @Override
        public String toString() {
            switch(this) {
                case North: return "North";
                case South: return "South";
                default: throw new IllegalArgumentException();
            }
        }

        public String toStringAbv() {
            switch(this) {
                case North: return "N";
                case South: return "S";
                default: throw new IllegalArgumentException();
            }
        }
    }

    public enum EastWest {
        East(0),
        West(1);

        private final int value;

        EastWest(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static EastWest parse(int id) {
            EastWest[] types = values();
            if(types.length > id && id > -1)
                return types[id];
            throw new IllegalArgumentException("Invalid NorthSouth id: " + id);
        }

        public static EastWest parse(String value) {
            if (value == null) {
                throw new NullPointerException();
            }

            switch(value.toLowerCase()) {
                case "e":
                case "east": return East;
                case "w":
                case "west": return West;
                case "": return null;
                default: throw new IllegalArgumentException(String.format("Invalid EastWest Name: %s", value));
            }
        }

        @Override
        public String toString() {
            switch(this) {
                case East: return "East";
                case West: return "West";
                default: throw new IllegalArgumentException();
            }
        }

        public String toStringAbv() {
            switch(this) {
                case East: return "E";
                case West: return "W";
                default: throw new IllegalArgumentException();
            }
        }
    }


    public enum UomElevation {
        Feet(0),
        Meters(1);

        private final int value;

        UomElevation(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static UomElevation parse(int id) {
            UomElevation[] types = values();
            if(types.length > id && id > -1)
                return types[id];
            throw new IllegalArgumentException(String.format("Invalid UomAltitude id: %d", id));
        }

        public static UomElevation parse(String value) {
            if (value == null) {
                throw new NullPointerException();
            }

            switch(value.toLowerCase()) {
                case "m":
                case "meters": return Meters;
                case "f":
                case "feet": return Feet;
                default: throw new IllegalArgumentException(String.format("Invalid UomAltitude Name: %s", value));
            }
        }

        @Override
        public String toString() {
            switch(this) {
                case Feet: return "Feet";
                case Meters: return "Meters";
                default: throw new IllegalArgumentException();
            }
        }


        public String toStringAbv() {
            switch(this) {
                case Feet: return "Ft";
                case Meters: return "M";
                default: throw new IllegalArgumentException();
            }
        }
    }
}
