package hu.sovaroq.framework.service.useless;

import hu.sovaroq.framework.core.bus.EventListener;
import hu.sovaroq.framework.service.base.AbstractService;
import hu.sovaroq.framework.service.base.Service;
import hu.sovaroq.framework.service.features.Tick;

/**
 * For test purposes.
 *
 * Created by balazs_horvath on 2/23/2017.
 */
@Service
@EventListener
public class CompletelyUselessService extends AbstractService<Object>{

    @Override
    public void start(Object o) {
        log.info(">CompletelyUselessService - start()");
        super.start(o);
        log.info("<CompletelyUselessService - start()");
    }

    @Override
    public void stop() {
        log.info(">CompletelyUselessService - stop()");
        super.stop();
        log.info("<CompletelyUselessService - stop()");
    }

    public void onEvent(IUselessEvents.RequestNothing request){
        log.info("Received some bullshit: " + request);

        post(new IUselessEvents.HereIsNothing("This is not an information."));
    }

    @Tick(1000)
    public void doNothing(){
        log.info("I'm consuming resources for nothing.");
    }

}
