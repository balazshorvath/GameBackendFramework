package hu.sovaroq.framework.service;

import hu.sovaroq.framework.events.FrameworkEvent;

/**
 * A Controller is a container, a collection of services separated from the framework.
 *
 * A Controller is listening for one base type of event, which will be posted on the
 * inner event bus.
 *
 * A Controller will create/update/stop it's own services based on a configuration implementation.
 *
 * The E type parameter is the base event type, that the Controller and it's contents/services will need to sit on.
 *
 *
 * Why IController exists?
 *  First, the idea was to have services, which can be published inside the Framework, or inside other services
 *  to have the ability to separate the game services, scripts from chat, authentication.
 *  It is probably better to have the authentication in a different place, than the actual game.
 *
 *  So there's a service hierarchy: services have parents, children. But you have to differentiate
 *  the services, who can be started in the Framework - MasterService - and who can only be a child of another service.
 *  This means MasterService extends Service, since it has the same capabilities, plus some extra.
 *  If the child service needs to be separated, it means mainly, that it's contained in a different Controller,
 *  with a different event bus, otherwise there's no separation. So a parent is necessary, which will be something,
 *  that implements Service.
 *  Since MasterService is a service, although has a pretty different role already, still has to keep it's service
 *  capabilities. But how is one forced to do it properly? What will be the MasterService's parent? The Framework
 *  cannot be a service, so you set it to null and hack the system?
 *  There's no restriction anymore, which leads to confusion about the design.
 *
 *  Conclusion is Controller instead of MasterService. Every service can only run in a Controller.
 *  Services cannot have child services. A service communicates only through it's Controller.
 *  There's a default FrameworkController, which is subscribed to every message to be able to run any service, who
 *  shouldn't have a Controller on it's own. They fire events directly to the Framework.
 *  For example the ServiceManager, or one could create an event logger.
 *
 *
 * Created by Oryk on 2017. 01. 24..
 */
public interface IController<E extends FrameworkEvent, Config>{
    void start(Config config);
    void stop();

    String getControllerId();

    void onEvent(E event);
}
