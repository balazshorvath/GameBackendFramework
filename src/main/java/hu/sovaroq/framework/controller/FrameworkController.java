package hu.sovaroq.framework.controller;

import java.util.List;

import hu.sovaroq.framework.controller.base.AbstractController;
import hu.sovaroq.framework.controller.base.Context;
import hu.sovaroq.framework.core.ServiceManager;
import hu.sovaroq.framework.service.base.IService;

/**
 * Created by Oryk on 2017. 01. 27..
 */
public class FrameworkController extends AbstractController<Context> {

    @Override
    public void start(Context context) {
    	manager = new ServiceManager(context.getBus(), 5, 20, 20);
    	
    	
    	manager.start();
    }

    @Override
    public void stop() {
    	manager.stop();
    }
	@Override
	public String getStatusDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getWorkload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IService> getServices() {
		// TODO Auto-generated method stub
		return null;
	}
}
