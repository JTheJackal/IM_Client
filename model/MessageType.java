/**
 * message state
 */
package model;

public interface MessageType {

	String message_LoginSuccess="1";//login success
	String message_login_fail="2";//login failed
	String message_comm_mes="3";//common message
	String message_get_onLineFriend="4";//get online friends
	String message_ret_onLineFriend="5";//returen online friends
	String message_createAcc = "6";
	String message_login = "7";
	String message_createAccSuccess = "8";
	String message_createAccFail = "9";
	String message_addFriend = "10";
	String message_addFriendSuccess = "11";
	String message_addFriendFail = "12";
	String message_getFriends = "13";
	String message_sendFriends = "14";
}