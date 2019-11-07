package com.valpha.rpcapp.Contract;

public class RpcApp {
    public static final String TAG = "RpcAppFragment";

    public interface Communication {
        String MODE = "rpcMode";
        String MODE_DEFAULT = "UART";
        String MODE_UART = "UART";
        String MODE_SPI = "SPI";
        String MODE_CAN = "CAN";
        String[] MODE_LIST = {MODE_UART, MODE_SPI, MODE_CAN};

        interface Default {
            interface Uart {

                int BAUDRATE = 115200;
                Integer[] BAUDRATE_LIST = {9600, 19200, 38400, 57600, 115200};
                int DATABITS = 8;
                Integer[] DATABITS_LIST = {7, 8};
                int STOPBIT = 1;
                Integer[] STOPBIT_LIST = {1, 2};
                char PARITY = 'N';
                Character[] PARITY_LIST = {'N', 'E', 'O', '\0', 'S'};
            }
        }

        class Uart {
            private int mBandrate;
            private int mDataBits;
            private int mStopBit;
            private char mParity;

            public Uart() {
                this(Default.Uart.BAUDRATE);
            }

            public Uart(int BAUDRATE) {
                this(BAUDRATE, Default.Uart.DATABITS);
            }

            public Uart(int BAUDRATE, int DATABITS) {
                this(BAUDRATE, DATABITS, Default.Uart.STOPBIT);
            }

            public Uart(int BAUDRATE, int DATABITS, int STOPBIT) {
                this(BAUDRATE, DATABITS, STOPBIT, Default.Uart.PARITY);
            }

            public Uart(int BAUDRATE, int DATABITS, int STOPBIT, char PARITY) {
                this.mBandrate = BAUDRATE;
                this.mDataBits = DATABITS;
                this.mStopBit = STOPBIT;
                this.mParity = PARITY;
            }

            public int getmBandrate() {
                return mBandrate;
            }

            public int getmDataBits() {
                return mDataBits;
            }

            public int getmStopBit() {
                return mStopBit;
            }

            public void setmBandrate(int mBandrate) {
                this.mBandrate = mBandrate;
            }

            public void setmDataBits(int mDataBits) {
                this.mDataBits = mDataBits;
            }

            public void setmStopBit(int mStopBit) {
                this.mStopBit = mStopBit;
            }

            public void setmParity(char mParity) {
                this.mParity = mParity;
            }

            public char getmParity() {
                return mParity;
            }
        }


    }
}
