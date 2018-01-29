/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import databaseclasses.Chat;
import databaseclasses.Users;
import java.util.ArrayList;

/**
 *
 * @author abanoub samy
 */
public interface ChatDaoInterface extends DaoInterface<Chat>{

    /**
     *
     * @param user
     * @param friend
     * @return
     */
    public Chat select_individualChat(Users user,Users friend);

    /**
     *
     * @param user
     * @param friend
     * @return
     */
    public int insert_individualChat(Users user,Users friend);

    /**
     *
     * @param user
     * @param type
     * @return
     */
    public ArrayList<Chat> select_allChats(Users user,String type);

    /**
     *
     * @param user
     * @return
     */
    public ArrayList<Chat> select_allRecentChats(Users user);

    /**
     *
     * @param chat
     * @param users
     * @return
     */
    public boolean insert_chatMemberTable(Chat chat, ArrayList<Users> users);

    /**
     *
     * @param chat
     * @return
     */
    public Chat selectChatByName(Chat chat);
  
}
