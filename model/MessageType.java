/**
 * message state
 */
package model;

public interface MessageType {

	String message_succeed="1";//login success
	String message_login_fail="2";//login failed
	String message_comm_mes="3";//common message
	String message_get_onLineFriend="4";//get online friends
	String message_ret_onLineFriend="5";//returen online friends
	String message_createAcc = "6";
	String message_login = "7";
}