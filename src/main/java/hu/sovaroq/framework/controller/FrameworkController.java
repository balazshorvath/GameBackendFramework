package hu.sovaroq.framework.controller;

import java.util.List;

import hu.sovaroq.framework.controller.base.IController;
import hu.sovaroq.framework.events.FrameworkEvent;
import hu.sovaroq.framework.service.base.IService;

/**
 * Created by Oryk on 2017. 01. 27..
 */
//@Service()
public class FrameworkController implements IController<FrameworkEvent, Object> {
    @Override
    public void start(Object o) {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onEvent(FrameworkEvent event) {

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
