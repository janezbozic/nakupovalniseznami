package si.fri.prpo.nakupovalniseznami.zrno;

import si.fri.prpo.nakupovalniseznami.anotacije.BeleziKlice;
import si.fri.prpo.nakupovalniseznami.stevec.Stevec;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevZrno {

    @Inject
    private Stevec stevec;

    Logger log = Logger.getLogger(BelezenjeKlicevZrno.class.getName());

    @AroundInvoke
    public Object beleziKlic(InvocationContext context) throws Exception {
        stevec.povecajStevec();
        log.info("Klic stevilka: " + stevec.getStevec());

        return context.proceed();
    }

}