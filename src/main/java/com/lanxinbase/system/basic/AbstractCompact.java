package com.lanxinbase.system.basic;


import java.util.logging.Logger;

/**
 * Created by alan.luo on 2017/9/18.
 */

public abstract class AbstractCompact {

    private Logger logger;

    public AbstractCompact(Class classz) {
        logger = Logger.getLogger(classz.getName());
    }

    public void logger(Object... args) {
        if (args.length > 0) {
            StringBuffer sb = new StringBuffer();
            for (Object o : args) {
                sb.append(o.toString()).append(" ");
            }
            logger.info(sb.toString());
        } else {
            logger.info("nil");
        }

    }

    public void println(Object o) {
        if (o == null) {
            System.out.println("Compact>>null");
        } else {
            System.out.println("Compact>>" + o.toString());
        }
    }

}
