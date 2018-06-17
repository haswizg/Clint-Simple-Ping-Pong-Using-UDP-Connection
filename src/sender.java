import java.io.IOException;
import java.net.*;
import java.time.Duration;
import java.time.LocalTime;


public class sender extends Thread{

    private final static int PORT=2018;
    private final static InetAddress host;

    private final static byte[] ipAddr = new byte[] { 127, 0, 0, 1 };

    static {InetAddress host1;
        try {
            host1 = InetAddress.getByAddress(ipAddr);
        } catch (UnknownHostException e) {
            host1 = null;
            e.printStackTrace();
        }
        host = host1;
    }

    private static int NumberCount=0;
    private final int msgNumber;
    private LocalTime sendTime;
    private LocalTime receiveTime;


    public void run(){
        try(DatagramSocket socket = new DatagramSocket(0)){
            socket.setSoTimeout(1000);
            String id=String.valueOf(msgNumber);
            byte[] data = id.getBytes("US-ASCII");

            DatagramPacket requst = new DatagramPacket(data, data.length, host, PORT);
            DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
            socket.send(requst);

            this.sendTime = LocalTime.now();
            System.out.println("massage NO."+msgNumber +" send ...");
            try{
                socket.receive(response);
                this.receiveTime = LocalTime.now();

                Duration duration = Duration.between(sendTime, receiveTime);
                long durationDays = duration.toMillis();

                //LocalTime d = LocalTime.ofInstant(sendTime,receiveTime);
                String result = new String(response.getData(), 0, response.getLength(), "US-ASCII");
                System.out.println("massage NO."+msgNumber +" receive ...\n"+result+" take "+durationDays+"ms");
            }catch (SocketTimeoutException ex){
                System.out.println("massage NO."+msgNumber +" TimeOUT !!!");
            }

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private sender(){
        msgNumber=++NumberCount;
    }

    public static void main(String[] args) {

        Thread msg0 = new Thread(new sender());
        Thread msg1 = new Thread(new sender());
        Thread msg2 = new Thread(new sender());
        Thread msg3 = new Thread(new sender());
        Thread msg4 = new Thread(new sender());
        Thread msg5 = new Thread(new sender());
        Thread msg6 = new Thread(new sender());
        Thread msg7 = new Thread(new sender());
        Thread msg8 = new Thread(new sender());
        Thread msg9 = new Thread(new sender());

        msg0.start();
        msg1.start();
        msg2.start();
        msg3.start();
        msg4.start();
        msg5.start();
        msg6.start();
        msg7.start();
        msg8.start();
        msg9.start();

    }
}