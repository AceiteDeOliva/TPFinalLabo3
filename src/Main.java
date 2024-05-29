
import Modelos.Entidades.Personaje;
import Modelos.Sistema.Ejecucion;


public class Main {
    public static void main(String[] args) {

        Ejecucion.Ejecucion();

        //por si acaso
        /*String art1 =
                "     _,.\n" +
                        "    ,` -.)\n" +
                        "   ( _/-\\\\-._\n" +
                        "  /,|`--._,-^|            ,\n" +
                        "  \\_| |`-._/||          ,'|\n" +
                        "    |  `-, / |         /  /\n" +
                        "    |     || |        /  /\n" +
                        "     `r-._||/   __   /  /\n" +
                        " __,-<_     )`-/  `./  /\n" +
                        "'  \\   `---'   \\   /  /\n" +
                        "    |           |./  /\n" +
                        "    /           //  /\n" +
                        "\\_/' \\         |/  /\n" +
                        " |    |   _,^-'/  /\n" +
                        " |    , ``  (\\/  /_\n" +
                        "  \\,.->._    \\X-=/^\n" +
                        "  (  /   `-._//^`\n" +
                        "   `Y-.____(__}\n" +
                        "    |     {__)\n" +
                        "          ()";


        String art2 =
                "       ,     .\n" +
                        "        /(     )\\               A\n" +
                        "   .--.( `.___.' ).--.         /_\\\n" +
                        "   `._ `%_&%#%$_ ' _.'     /| <___> |\\\n" +
                        "      `|(@\\*%%/@)|'       / (  |L|  ) \\\n" +
                        "       |  |%%#|  |       J d8bo|=|od8b L\n" +
                        "        \\ \\$#%/ /        | 8888|=|8888 |\n" +
                        "        |\\|%%#|/|        J Y8P\"|=|\"Y8P F\n" +
                        "        | (.\".)%|         \\ (  |L|  ) /\n" +
                        "    ___.'  `-'  `.___      \\|  |L|  |/\n" +
                        "  .'#*#`-       -'$#*`.       / )|\n" +
                        " /#%^#%*_ *%^%_  #  %$%\\    .J (__)\n" +
                        " #&  . %%%#% ###%*.   *%\\.-'&# (__)\n" +
                        " %*  J %.%#_|_#$.\\J* \\ %'#%*^  (__)\n" +
                        " *#% J %$%%#|#$#$ J\\%   *   .--|(_)\n" +
                        " |%  J\\ `%%#|#%%' / `.   _.'   |L|\n" +
                        " |#$%||` %%%$### '|   `-'      |L|\n" +
                        " (#%%||` #$#$%%% '|            |L|\n" +
                        " | ##||  $%%.%$%  |            |L|\n" +
                        " |$%^||   $%#$%   |  VK/cf     |L|\n" +
                        " |&^ ||  #%#$%#%  |            |L|\n" +
                        " |#$*|| #$%$$#%%$ |\\           |L|\n" +
                        " ||||||  %%(@)$#  |\\\\          |L|\n" +
                        " `|||||  #$$|%#%  | L|         |L|\n" +
                        "      |  #$%|$%%  | ||l        |L|\n" +
                        "      |  ##$H$%%  | |\\\\        |L|\n" +
                        "      |  #%%H%##  | |\\\\|       |L|\n" +
                        "      |  ##% $%#  | Y|||       |L|\n" +
                        "      J $$#* *%#% L  |E/\n" +
                        "      (__ $F J$ __)  F/\n" +
                        "      J#%$ | |%%#%L\n" +
                        "      |$$%#& & %%#|\n" +
                        "      J##$ J % %%$F\n" +
                        "       %$# * * %#&\n" +
                        "       %#$ | |%#$%\n" +
                        "       *#$%| | #$*\n" +
                        "      /$#' ) ( `%%\\\n" +
                        "     /#$# /   \\ %$%\\\n" +
                        "    ooooO'     `Ooooo";

        String[] art1Lines = art1.split("\n");
        String[] art2Lines = art2.split("\n");

        // Determine the maximum number of lines
        int maxLines = Math.max(art1Lines.length, art2Lines.length);

        // Print lines side by side
        for (int i = 0; i < maxLines; i++) {
            String line1 = i < art1Lines.length ? art1Lines[i] : "";
            String line2 = i < art2Lines.length ? art2Lines[i] : "";
            System.out.printf("%-100s %s%n", line1, line2); // Adjust spacing as needed


        }*/

    }
}


