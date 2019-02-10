/**
 * Author: Georgy Macharadze
 * Date: 09.02.2019
 */

import Configure.ProgramArgs;
import Web.WebManager;

public class Main {

    public static void main(String[] args) {
        ProgramArgs pargs = new ProgramArgs();
        if (pargs.parseArgs() != 0)
            return;

        WebManager web = new WebManager();
        Worker work = new Worker();
        int total_records = web.getTotalRecords(pargs);
        int offset = 0;
        String url;
        while (offset < total_records) {
            url = web.getUrlByOffset(pargs, offset);
            String jsonText = web.getContentByURL(url);

            work.work(jsonText, pargs);
            
            offset += pargs.pageSize;
        }
        return;
    }
}
