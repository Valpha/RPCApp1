package com.valpha.rpcapp.Contract;

public class RpcApp {
    public static final String TAG = "RpcAppFragment";

    public class Uart {
        public static final String BAUDRATE = "baudrate";
        public static final String DATABITS = "databits";
        public static final String STOPBITS = "stopbits";
        public static final String PARITY = "parity";
    }

    public interface Communication {
        String MODE = "rpcMode";
        String MODE_DEFAULT = "UART";
        String MODE_UART = "UART";
        String MODE_SPI = "SPI";
        String MODE_CAN = "CAN";

    }
}
