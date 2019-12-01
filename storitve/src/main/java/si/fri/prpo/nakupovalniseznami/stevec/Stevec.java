package si.fri.prpo.nakupovalniseznami.stevec;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Stevec {

    int stevec = 0;

    public void povecajStevec(){
        stevec++;
    }

    public int getStevec() {
        return stevec;
    }
}
