package cn.org.opendfl.tasktool.task;

import cn.hutool.core.text.CharSequenceUtil;
import cn.org.opendfl.tasktool.config.TaskToolConfiguration;
import cn.org.opendfl.tasktool.task.annotation.TaskCompute;
import cn.org.opendfl.tasktool.task.annotation.TaskComputeReq;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TaskCompute的vo对象
 */
@Data
@NoArgsConstructor
public class TaskComputeVo {
    public TaskComputeVo(TaskCompute taskCompute) {
        this.methodCode = taskCompute.methodCode();
        this.category = taskCompute.category();
        this.showProcessing = taskCompute.showProcessing();
        this.dataIdArg = "" + taskCompute.dataIdArgCount();
        this.userIdArg = "" + taskCompute.userIdArgCount();
        this.type = "taskCompute";
        this.sourceType="none";
    }

    /**
     * 方法编码，方法名，类名加方法名唯一，为空默认为当前方法名
     */
    private String methodCode;
    /**
     * 仅分类，没有其他作用
     */
    private String category;
    /**
     * 数据id的参数序号，-1表示无参
     * 0表示第一个参数，如果无参数的话也兼容
     * 建议放第1个，以免在中间增加参数时序号变了
     */
    private String dataIdArg;
    private String userIdArg;

    /**
     * 用于显示正在执行的数据
     */
    private boolean showProcessing;


    /**
     * 包名
     */
    private String pkg;
    /**
     * 类型
     */
    private String type;

    private String sourceType;


    public void readTaskParam(TaskToolConfiguration taskToolConfiguration, TaskComputeReq taskComputeReq) {
        this.type = taskComputeReq.getType();
        this.category = taskComputeReq.getCategory();
        if (CharSequenceUtil.isNotBlank(taskComputeReq.getMethodCode())) {
            this.methodCode = taskComputeReq.getMethodCode();
        }
        if (!CharSequenceUtil.equals("controller", taskComputeReq.getType())) {
            this.userIdArg = taskComputeReq.getUserIdParamName();
            this.dataIdArg = taskComputeReq.getDataIdParamName();
        } else {
            this.userIdArg = taskToolConfiguration.getControllerConfig().getUserIdField();
            this.dataIdArg = taskToolConfiguration.getControllerConfig().getDataIdField();
        }
    }
}
