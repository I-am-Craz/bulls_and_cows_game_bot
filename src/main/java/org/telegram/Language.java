package org.telegram;

enum Language{

    GAME_START{
        public  String getPhrase(String l){
            if (l.equals("ru")) {
                return "Хочешь начать новую игру? Поехали! Вводи четырёхзначное число.";
            } else {
                return "Do you want to start a new game? Let's go! Enter a four-digit number.";
            }
        }
    },
    GIVE_UP{
        public  String getPhrase(String l){
            if (l.equals("ru")) {
                return "Что, сдаёшься так быстро?)) Было загадано число: ";
            } else {
                return "What, are you giving up so fast?) There was a number: ";
            }
        }
    },
    HINTS_USED{
        public  String getPhrase(String l){
            if (l.equals("ru")) {
                return "Использовано подсказок : ";

            } else {
                return "Used hints : ";
            }
        }
    },
    GETTING_HINT_ERROR1{
        public  String getPhrase(String l){
            if (l.equals("ru")) {
                return "Мы ёще даже не начали новую игру, а ты уже сдаёшься...Чтобы начать новую игру используй команду /play . ";
            } else {
                return "We haven't even started a new game yet, and you're already giving up...To start a new game, use the command /play . ";
            }
        }
    },
    GETTING_HINT_ERROR2{
        public  String getPhrase(String l){
            if (l.equals("ru")) {
                return "Я не могу создать подсказку из ничего. Чтобы её получить нужно начать новую игру ( /play )!";
            } else {
                return "I can't create a hint from nothing. To get it you need to start a new game ( /play )!";
            }
        }
    },
    LETS_PLAY_AGAIN{
        public  String getPhrase(String l){
            if (l.equals("ru")) {
                return "Сыграем ещё?";
            } else {
                return "Shall we play again?";
            }
        }
    },
    HELP{
        public  String getPhrase(String l){
            if (l.equals("ru")) {
                return "/play - начать новую игру;\n/give_up - узнать, какое число было загадано;\n/hint - подсказка;\n/settings - настройки;\n/help - список всех команд.";
            } else {
                return "/play - start a new game;\n/give_up - find out what number has been puzzled;\n/hint - hint;\n/settings - settings;\n/help - a list of all commands.";
            }
        }
    },
    GAME_RESULT{
        public  String getPhrase(String l){
            if (l.equals("ru")) {
                return "Поздравляю!\nКоличество попыток : ";
            } else {
                return "Congratulations!\nNumber of attempts : ";
            }
        }
    },
    COWS{
        public  String getPhrase(String l){
            if (l.equals("ru")) {
                return "Коров : ";
            } else {
                return "Cows : ";
            }
        }
    },
    BULLS{
        public  String getPhrase(String l){
            if (l.equals("ru")) {
                return "Быков : ";
            } else{
                return "Bulls : ";
            }
        }
    },
    ERROR{
        public  String getPhrase(String l){
            if (l.equals("ru")) {
                return "Я не понимаю тебя :( \nЛибо используй команду /help, чтобы посмотреть полный список команд, либо введи четырёхзначное число.";
            } else {
                return "I don't understand you :( \nEither use /help to see the full list of commands, or enter a four-digit number.";
            }
        }
    };

    public abstract String getPhrase(String l);
}
