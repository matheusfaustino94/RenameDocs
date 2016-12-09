package br.com.pe.urbana.job;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzMain {
	
	

	public static void main(String[] args) throws SchedulerException {

	     
		
		
		
		// Define a job and tie it to our job class

		JobDetail job = JobBuilder.newJob(QuartzJob.class).build();

//		Trigger t1 = TriggerBuilder.newTrigger().withIdentity("SimpleTrigger")
//				.startNow().build();

		 Trigger t1 = TriggerBuilder.newTrigger().withIdentity("CronTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0 0/3 * 1/1 * ? *")).build();

		Scheduler sc = StdSchedulerFactory.getDefaultScheduler();

		sc.start();
		sc.scheduleJob(job, t1);

	}
}
