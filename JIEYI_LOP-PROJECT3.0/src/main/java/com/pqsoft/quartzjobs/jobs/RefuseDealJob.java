package com.pqsoft.quartzjobs.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pqsoft.letterOpinion.service.LetterOpinionService;
import com.pqsoft.quartzjobs.AbstractJob;

/**
 * 流程中拒绝、拒绝拉黑、客户放弃处理
 * @author zhengshangcheng
 *
 */
public class RefuseDealJob extends AbstractJob {

	@Override
	protected void exec(JobExecutionContext context)
			throws JobExecutionException {
		new LetterOpinionService().reproJob();
	}

}
