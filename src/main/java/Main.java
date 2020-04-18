/**
 * Author: Georgy Macharadze
 * Date: 09.02.2019
 */

import Configure.ProgramArgs;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        ProgramArgs pargs = new ProgramArgs();
        if (pargs.parseArgs() != 0)
            return;
        long start = System.currentTimeMillis();

        Worker work = new Worker();
        work.clearAll(pargs);

        Thread producer = new Thread(() -> {
            try {
                work.produce(pargs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                work.consume(pargs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        try {
            FileWriter writer = new FileWriter("LogFile.txt", true);
            writer.append(dateFormat.format(date) + " - " + dateFormat.format(new Date()) + ": Обновление успешно завершено\n");
            writer.close();
        } catch(IOException ex) {
            System.out.println("File write error\n");
        }

        System.out.println((System.currentTimeMillis() - start) / 60000.0);
        System.exit(0);
        return;
    }
}
