package co.yiiu;

import java.util.Random;

public class RandomNumber{
    //随机生成指定长度的数字+字符串  或 文字
    public static String getRandomNumberAndString(int lengthOfString){
        int i=0;
        int count=0;
        
        //final String chars="1、2、3、4、5、6、7、8、9、0、A、B、C、D、E、F、G、H、I、J、K、L、M、N、O、P、Q、R、S、T、U、V、W、X、Y、Z";

        final String chars = "见、明、问、力、理、尔、点、文、几、定、本、公、特、做、外、孩、相、西、果、走、将、月、十、实、向、声、车、全、信、重、"
                + "三、机、工、物、气、每、并、别、真、打、太、新、比、才、便、夫、再、书、部、水、像、眼、等、体、却、加、电、主、界、门、利、海、受、听、表、"
                + "德、少、克、代、员、许、稜、先、口、由、死、安、写、性、马、光、白、或、住、难、望、教、命、花、结、乐、色、的、一、是、了、我、不、人、在、他、"
                + "有、这、个、上、们、来、到、时、大、地、为、子、中、你、说、生、国、年、更、拉、东、神、记、处、让、母、父、应、直、字、场、帄、报、友、关、放、"
                + "至、张、认、接、告、入、笑、内、英、军、候、民、岁、往、何、度、山、觉、路、带、万、男、边、风、解、叫、任、金、快、原、吃、妈、变、通、师、立、象、数、"
                + "四、失、满、战、远、格、士、音、轻、目、条、呢、病、始、达、深、完、今、提、求、清、王、化、空、业、思、切、怎、非、找、片、罗、钱、紶、吗、语、元、喜、"
                + "曾、离、飞、科、言、干、流、欢、约、各、即、指、合、反、题、必、该、论、交、终、林、请、医、晚、制、球、决、窢、传、画、保、读、运、及、则、房、早、院、"
                + "量、苦、火、布、品、近、坐、产、答、星、精、视、五、连、司、巴、奇、管、类、未、朋、且、婚、台、夜、青、北、队、久、乎、越、观、落、尽、形、影、红、爸、百、"
                + "着、就、那、和、要、她、出、也、得、里、后、自、以、会、家、可、下、而、过、天、去、能、对、小、多、然、于、心、学、么、之、都、好、看、"
                + "起、发、当、没、成、只、如、事、把、还、用、第、样、道、想、作、种、开、美、总、从、无、情、己、面、最、女、但、现、前、些、所、同、"
                + "日、手、又、行、意、动、方、期、它、头、经、长、儿、回、位、分、爱、老、因、很、给、名、法、间、斯、知、世、什、两、次、使、身、者、"
                + "被、高、已、亲、其、进、此、话、常、与、活、正、感、甲、乙、丙、丁、戊、己、庚、辛、壬、癸、子、丑、寅、卯、辰、巳、午、未、申、酉、戌、亥";
        String[] charArr=chars.split("、");//以、为分隔符，存在数组里

        StringBuffer randomStr = new StringBuffer("");
        Random rnd=new Random();
        int strlen = charArr.length;
        
        while(count < lengthOfString){
            i = rnd.nextInt(strlen);
            randomStr.append(charArr[i]);
            count++;
        }
        return randomStr.toString().toLowerCase();
    }
    
    
    //随机生成指定范围内的数字，不包含参数本身
    public static int getExtentRandomNumber(int extent) {
        int number = (int) (Math.random() * extent);
        return number;
    }
    
    //main方法测试
    public static void main(String[] args) {
        System.out.println(getRandomNumberAndString(120));
    }
}