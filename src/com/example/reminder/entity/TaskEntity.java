package com.example.reminder.entity;

public class TaskEntity {

	private String taskName;
	private Integer id;
	private String taskDueDate;
	private String taskStatus;
	private String taskGeo;
	private String taskId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDueDate() {
		return taskDueDate;
	}

	public void setTaskDueDate(String taskDueDate) {
		this.taskDueDate = taskDueDate;
	}

	public String getTaskGeo() {
		return taskGeo;
	}

	public void setTaskGeo(String taskGeo) {
		this.taskGeo = taskGeo;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Override
	public String toString() {
		return "TaskEntity [taskName=" + taskName + ", id=" + id
				+ ", taskDueDate=" + taskDueDate + ", taskStatus=" + taskStatus
				+ ", taskGeo=" + taskGeo + ", taskId=" + taskId + "]";
	}

}
