/**
 * Author: Georgy Macharadze
 * Date: 09.02.2019
 */

import Configure.ProgramArgs;
import org.json.simple.parser.ParseException;

public class Main {

    public static void main(String[] args) throws InterruptedException {
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

        System.out.println((System.currentTimeMillis() - start) / 60000.0);
        return;
    }
}
