/**
 * Author: Georgy Macharadze
 * Date: 09.02.2019
 */

import Configure.ProgramArgs;

public class Main {

    public static void main(String[] args) {
        ProgramArgs pargs = new ProgramArgs();
        if (pargs.parseArgs() != 0)
            return;

        return;
    }
}
