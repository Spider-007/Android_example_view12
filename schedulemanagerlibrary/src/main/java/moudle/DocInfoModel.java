//package moudle;
//
//
//import com.htmitech.myEnum.LogManagerEnum;
//
//public class DocInfoModel extends BaseModel {
//	public static final int TYPE_GET_DETAILTASK = 0;
//	public static final int TYPE_GET_FLOW = 1;
//	public static final int TYPE_GET_WORD_TEXTTASK = 2;
//	public static final int TYPE_GET_DOWNLOAD_DOCISFINISH = 3;
//	public static final int TYPE_GET_DOWNLOAD_ATTISFINISH = 4;
//	public static final int TYPE_DOACTION_TASK = 5;
//	public static final int TYPE_DOACTION_TASK_START = 9;
//	public static final int TYPE_START_DETAILTASK = 6; //发起流程
//	public static final int TYPE_START_DETAILTASK_BUILD = 10; //发起流程
//	public static final int TYPE_START_DETAILTASK_H5 = 7;
//	public static final int TYPE_SAVEEXTFIELDS = 8;
//
//
//	private GetDetailTask mGetDetailTask;
//	private FlowTask mFlowTask;
//	private WorkTextTask mWordTextTask;
//	private DownFileIsFinish_DocFileTask mDownDocFileTask;
//	private DownFileIsFinish_AttachmentTask mDownAttachmentFileTask;
//	private DoActionTask mDoActionTask;
//	private StartDetailTask mStartDetailTask;
//	private H5StartDetailTask mH5StartDetailTask;
//	private SaveExtFieldsTask mSaveExtFieldsTask;
//
//
//	public DocInfoModel(IBaseCallback callback) {
//		super(callback);
//	}
//
//
//	@Override
//	protected BaseTaskBody createTask(int taskType, Object paramObject) {
//		switch (taskType) {
//			case TYPE_GET_DETAILTASK:
//				mGetDetailTask = new GetDetailTask(taskType);
//				mGetDetailTask.setFunctionCode(LogManagerEnum.APP_DOC_INFO.functionCode);
//				mGetDetailTask.buildRequestParam(paramObject);//paramObject ->  DocInfoParameters
//				return mGetDetailTask;
//			case TYPE_GET_FLOW:
//				mFlowTask = new FlowTask(taskType);
//				mFlowTask.setFunctionCode(LogManagerEnum.APP_DOC_FLOW.functionCode);
//				mFlowTask.buildRequestParam(paramObject); //paramObject ->  DocInfoParameters
//				return mFlowTask;
//			case TYPE_GET_WORD_TEXTTASK:
//				mWordTextTask = new WorkTextTask(taskType);
//				mWordTextTask.buildRequestParam(paramObject); //paramObject ->  DocInfoParameters
//				return mWordTextTask;
//			case TYPE_GET_DOWNLOAD_DOCISFINISH:
//				mDownDocFileTask = new DownFileIsFinish_DocFileTask(taskType);
//				mDownDocFileTask.buildRequestParam(paramObject);//paramObject ->  DocInfoParameters
//				return mDownDocFileTask;
//			case TYPE_GET_DOWNLOAD_ATTISFINISH:
//				mDownAttachmentFileTask = new DownFileIsFinish_AttachmentTask(taskType);
//				mDownAttachmentFileTask.buildRequestParam(paramObject); //paramObject ->  AttachmentInfoParameters
//				return mDownAttachmentFileTask;
//			case TYPE_DOACTION_TASK:
//				mDoActionTask = new DoActionTask(taskType);
//				mDoActionTask.setFunctionCode(LogManagerEnum.APP_DO_ACTION.functionCode);
//				mDoActionTask.buildRequestParam(paramObject); //paramObject -> DoActionParameter
//				return mDoActionTask;
//			case TYPE_DOACTION_TASK_START:
//				mDoActionTask = new DoActionTask(taskType);
//				mDoActionTask.setFunctionCode(LogManagerEnum.DODO_ACTION_NATIVE.functionCode);
//				mDoActionTask.buildRequestParam(paramObject); //paramObject -> DoActionParameter
//				return mDoActionTask;
//			case TYPE_START_DETAILTASK_H5:
//				mH5StartDetailTask = new H5StartDetailTask(taskType);
//				mH5StartDetailTask.setFunctionCode(LogManagerEnum.STARTWORKFLOWH5.functionCode);
//				mH5StartDetailTask.buildRequestParam(paramObject); //paramObject -> DoActionParameter
//				return mH5StartDetailTask;
//			case TYPE_START_DETAILTASK:
//				mStartDetailTask = new StartDetailTask(taskType);
//				mStartDetailTask.setFunctionCode(LogManagerEnum.STARTDOC_FLOW.functionCode);
//				mStartDetailTask.buildRequestParam(paramObject); //paramObject -> DoActionParameter
//				return mStartDetailTask;
//			case TYPE_START_DETAILTASK_BUILD:
//				mStartDetailTask = new StartDetailTask(taskType);
//				mStartDetailTask.setFunctionCode(LogManagerEnum.STARTDOC_FLOW.functionCode);
//				mStartDetailTask.buildRequestParam(paramObject); //paramObject -> DoActionParameter
//				return mStartDetailTask;
//			case TYPE_SAVEEXTFIELDS:
//				mSaveExtFieldsTask = new SaveExtFieldsTask(taskType);
//				mSaveExtFieldsTask.setFunctionCode(LogManagerEnum.GET_COMMONFORM_DO_ACTION.functionCode);
//				mSaveExtFieldsTask.buildRequestParam(paramObject); //paramObject -> DoActionParameter
//				return mSaveExtFieldsTask;
//		}
//		return super.createTask(taskType, paramObject);
//	}
//
//}
