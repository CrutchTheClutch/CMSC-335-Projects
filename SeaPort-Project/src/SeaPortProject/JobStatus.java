package SeaPortProject;

/**
 * <strong>Filename:</strong> &emsp;&emsp;&emsp; {@code JobStatus} <br/>
 * <strong>Author:</strong> &emsp;&emsp;&emsp;&emsp; William Crutchfield <br/>
 * <strong>Date Created:</strong> &emsp; March 4th, 2019 <br/>
 *
 * <br/>
 *
 * Defines the {@code JobStatus} enum.  Is used to determine GUI elements for each {@link Job}.
 *
 * <br/><br/>
 *
 * The various {@link Job} statuses can be one of the following:
 * <ul>
 *     <li>RUNNING - Is currently executing.</li>
 *     <li>SUSPENDED - Is paused.</li>
 *     <li>WAITING - Is waiting to execute.</li></li>
 *     <li>DONE - Has successfully executed.</li></li>
 *     <li>CANCELLED - Has been cancelled.</li>
 * </ul>
 *
 * @author  William Crutchfield
 * @see     Job
 */
enum JobStatus {RUNNING, SUSPENDED, WAITING, DONE, CANCELED}
