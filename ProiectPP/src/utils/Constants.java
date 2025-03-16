package utils;

public class Constants {

    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants{
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int GROUND = 3;
        public static final int FALLING = 4;
//        public static final int HIT = 5;
        public static final int ATTACK_1 = 5;
        public static final int ATTACK_2 = 6;
        public static final int ATTACK_3 = 7;
        public static final int ATTACK_HOLD = 8;

        public static int GetSpriteAmount(int player_action){
            switch(player_action){
                case RUNNING:
                    return 16;
                case IDLE:
                    return 24;
                case JUMP:
                    return 32;
                case FALLING:
                    return 33;
                case GROUND:
                    return 19;
                case ATTACK_1:
                    return 18;
                case ATTACK_2:
                    return 16;
                case ATTACK_3:
                    return 17;
                case ATTACK_HOLD:
                    return 21;
                default:
                    return 1;

            }
        }
    }
}
