package sinhala_tamil_ocr;

import java.util.regex.*;

/**
 * <p>Title: Encoding Conveter Utility v4.0.0</p>
 * <p>Description: Convert tamil ans sinhala encodings to Unicode</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: University of Colombo School of Computing</p>
 * @author Viraj Welgama
 * @version 4.0.0
 */


public class Normalizer {

  //Function for replace given patterns of the word
  private String RegXExecuter(String pattern, String word, String replace,
                              int join, boolean last) {

    Pattern p;
    Matcher m;
    StringBuffer result;
    String repOri = new String(replace.toString());

    p = Pattern.compile(pattern);
    m = p.matcher(word);
    result = new StringBuffer();

    while (m.find()) {

      if (join > 0) {
        if (last)
          replace = m.group().substring(join).concat(repOri);
        else
          replace = repOri.concat(m.group().substring(0, join));
      }

      m.appendReplacement(result, replace);

    }
    m.appendTail(result);

    return result.toString();

  } // RegXExecuter()

  //Function for replace given patterns of the word and ammend from back
  private String RegXExecuterRev(String pattern, String word, String replace,
                                 int join) {

    Pattern p;
    Matcher m;
    StringBuffer result;
    String repOri = new String(replace.toString());

    p = Pattern.compile(pattern);
    m = p.matcher(word);
    result = new StringBuffer();

    while (m.find()) {

      replace = repOri.concat(m.group().substring(join));

      m.appendReplacement(result, replace);

    }

    m.appendTail(result);

    return result.toString();

  } // RegXExecuter()

  // Normalize Sinhala unicode values
  public String SinhalaNormalize(String word) {

/////////// Arrage Letters ////////////////////////////////////////

    // Arrage Murthaja Luu
    word = RegXExecuter("\u0dfe\u0db5", word, "\u0dc5\u0dd4", 0, true);
    word = RegXExecuter("\u0dc5\u0dd4\u0dd1", word, "\u0dc5\u0dd6", 0, true);

    // Arrange Ru
    word = RegXExecuter("\u0dbb\u0dd0", word, "\u0dbb\u0dd4", 0, true);

    // Arrange Rea
    word = RegXExecuter("\u0dbb\u0df4\u0dd0", word, "\u0dbb\u0dd0", 0, true);

    // Arrange gna
    word = RegXExecuter("\u0d9a\u0dca\u200d\u0daf", word, "\u0da4", 0, true);

    // Arrange Mahaprana Ja
    word = RegXExecuter("\u0d9a\u0dca\u200d\u0db0", word, "\u0da3", 0, true);

    // Arrange Ruu
    word = RegXExecuter("\u0dbb\u0dd1", word, "\u0dbb\u0dd6", 0, true);

    // Arrange Reaea
    word = RegXExecuter("\u0dbb\u0df4\u0dd1", word, "\u0dbb\u0dd1", 0, true);


    // Arrange Aa
    word = RegXExecuter("\u0d85\u0dcf", word, "\u0d86", 0, true);

    // Arrange Ae
    word = RegXExecuter("\u0d85\u0dd0", word, "\u0d87", 0, true);

    // Arrange Aee
    word = RegXExecuter("\u0d85\u0dd1", word, "\u0d88", 0, true);

    // Arrange Uu
    word = RegXExecuter("\u0d8b\u0ddf", word, "\u0d8c", 0, true);

    // Arrange Iru
    word = RegXExecuter("\u0d8d\u0dd8", word, "\u0d8e", 0, true);

    // Arrange Ilu
    word = RegXExecuter("\u0d8f\u0ddf", word, "\u0d90", 0, true);

    // Arrange Ee
    word = RegXExecuter("\u0d91\u0dca", word, "\u0d92", 0, true);

    // Arrange Ai
    word = RegXExecuter("\u0dd9\u0d91", word, "\u0d93", 0, true);

    // Arrange Oo
    word = RegXExecuter("\u0d94\u0dca", word, "\u0d95", 0, true);

    // Arrange Ai
    word = RegXExecuter("\u0d94\u0ddf", word, "\u0d96", 0, true);

    // Arrange modifier Iruu
    word = RegXExecuter("\u0dd8\u0dd8", word, "\u0df2", 0, true);

    // Arrange modifier Ai
    word = RegXExecuter("\u0dd9\u0dd9", word, "\u0ddb", 0, true);

    // Arrange Sangnaka Ga
    word = RegXExecuter("\u0dfe\u0d9c", word, "\u0d9f", 0, true);

    // Arrange Sangnaka Ja
    word = RegXExecuter("\u0dfe\u0da2", word, "\u0da6", 0, true);

    // Arrange Sangnaka Da
    word = RegXExecuter("\u0dfe\u0da9", word, "\u0dac", 0, true);
    word = RegXExecuter("\u0dfe\u0d9e", word, "\u0dac", 0, true);

    // Arrange Sangnaka Dha
    word = RegXExecuter("\u0dfe\u0daf", word, "\u0db3", 0, true);

    // Arrange Bendi Akura ndha
    word = RegXExecuter("\u0dfe\u0db0", word, "\u0daf\u0dca\u0200d\u0db0", 0, true);

/////////// Normalize Unicode /////////////////////////////////////////////


    // Shift the Rakar to right
    word = RegXExecuter(".\u0dbb\u0dca\u200d", word, "\u0dbb\u0dca\u200d", 1, false);

    // Shift the Kombo to right
    word = RegXExecuter("\u0dd9.", word, "\u0dd9", 1, true);

    // Shift the Kombo beyond ZWJ
    word = RegXExecuter("\u0dd9\u0dca\u200d.", word, "\u0dd9", 1, true);

    // Shift the Kombo beyond ZWNJ
    word = RegXExecuter("\u0dd9\u0dca\u200c.", word, "\u0dd9", 1, true);

    // Shift the Modifier Ai to right
    word = RegXExecuter("\u0ddb.", word, "\u0ddb", 1, true);

    // Shift the Modifier Ai beyond ZWJ
    word = RegXExecuter("\u0ddb\u0dca\u200d.", word, "\u0ddb", 1, true);

    // Shift the Modifier Ai beyond ZWNJ
    word = RegXExecuter("\u0ddb\u0dca\u200c.", word, "\u0ddb", 1, true);

    // Arrange Modifier Ee
    word = RegXExecuter("\u0dd9\u0dca", word, "\u0dda", 0, true);

    // Arrange Modifier Oo
    word = RegXExecuter("\u0dd9\u0dcf\u0dca", word, "\u0ddd", 0, true);

    // Arrange Modifier O
    word = RegXExecuter("\u0dd9\u0dcf", word, "\u0ddc", 0, true);

    // Arrange Modifier Au
    word = RegXExecuter("\u0dd9\u0ddf", word, "\u0dde", 0, true);

    // Remove Extra Sanchana Simbols
    word = RegXExecuter("\u0dfe", word, "", 0, true);

    // Clean up miss tryping
    word = RegXExecuter("\u0dca+", word, "", 1, false);
    word = RegXExecuter("\u0dd2+", word, "", 1, false);
    word = RegXExecuter("\u0dd3+", word, "", 1, false);
    word = RegXExecuter("\u0dd4+", word, "", 1, false);
    word = RegXExecuter("\u0dd6+", word, "", 1, false);

    // Shift the Rakar to right to avoid typing sequence errors
    word = RegXExecuter("\u0dd2\u0dca\u200d\u0dbb", word, "\u0dca\u200d\u0dbb\u0dd2", 0, false);
    word = RegXExecuter("\u0dd3\u0dca\u200d\u0dbb", word, "\u0dca\u200d\u0dbb\u0dd3", 0, false);
    word = RegXExecuter("\u0dda\u0dca\u200d\u0dbb", word, "\u0dca\u200d\u0dbb\u0dda", 0, false);

    return word;

  } // Sinhalanormalize()

  // Normalize Sinhala unicode values
  public String TamilNormalize(String word) {

    // Arrange Au
    word = RegXExecuter("\u0b92\u0bb3", word, "\u0b94", 0, true);

    // Arrange Mod Uu
    word = RegXExecuter("\u0bc1\u0bc2", word, "\u0bc2", 0, true);

    // Make R by  Mod Aa
   word = RegXExecuterRev("\u0bbe[\u0bcd|\u0bbf|\u0bc0]", word, "\u0bb0", 1);

    // Remove extra pulli
    word = RegXExecuter("\u0bcd+", word, "", 1, false);

    // Shift the Kombo to right
    word = RegXExecuter("\u0bc6.", word, "\u0bc6", 1, true);

    // Shift the Kombo oo to right
    word = RegXExecuter("\u0bc7.", word, "\u0bc7", 1, true);

    // Shift the Kombo ai to right
    word = RegXExecuter("\u0bc8.", word, "\u0bc8", 1, true);

    // Arrange Modifier O
    word = RegXExecuter("\u0bc6\u0bbe", word, "\u0bca", 0, true);

    // Arrange Modifier Oo
    word = RegXExecuter("\u0bc7\u0bbe", word, "\u0bcb", 0, true);

    // Arrange Modifier Au
    word = RegXExecuter("\u0bc6\u0bd7", word, "\u0bcc", 0, true);

    return word;
  } //TamilNormalise()

} //class
