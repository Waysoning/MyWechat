package com.example.todo;

import java.sql.Time;

public class ToDolistinfo {
        private String event;
        private String date;
        private int process=0;
        public String getEvent(){
            return event;
        }

        public void setEvent(String event)
        {
            this.event=event;
        }

        public String getDate(){
            return date;
        }
        public void setDate(String date){
            this.date=date;
        }


        public void setProcess(int pro){
            this.process=pro;
        }
        public int getProcess(){
            return process;
    }

}
