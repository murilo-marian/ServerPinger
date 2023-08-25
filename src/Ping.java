import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ping {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        List<Server> servidores = new ArrayList<>();
        Server server1 = new Server("172.16.3.18", "servidor 1", "victor@gmail.com");
        servidores.add(server1);
        System.out.print("Tempo entre testes: ");
        int tempoEntreTestes = entrada.nextInt();
        int cont = 0;
        while (true) {
            try {
                Thread.sleep(tempoEntreTestes);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (Server servidore : servidores) {
                try {
                    if (InetAddress.getByName(servidore.getIp()).isReachable(5000)){

                        if (servidore.getFirstSuccessfultVerification() == null) {
                            servidore.setFirstSuccessfultVerification(LocalTime.now());
                            servidore.setLastSuccessfulVerification(LocalTime.now());
                            servidore.setFirstInactiveVerification(null);
                            servidore.setLastInactiveVerification(null);
                        } else {
                            servidore.setLastSuccessfulVerification(LocalTime.now());
                            servidore.setFirstInactiveVerification(null);
                            servidore.setLastInactiveVerification(null);
                        }
                        System.out.println("ok");
                    } else {
                        cont++;
                        if (servidore.getFirstInactiveVerification() == null) {
                            servidore.setFirstInactiveVerification(LocalTime.now());
                            servidore.setLastInactiveVerification(LocalTime.now());
                        } else {
                            servidore.setLastInactiveVerification(LocalTime.now());
                        }
                        System.out.println(servidore.showLog(cont));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
