package com.usda.fmsc.geospatial.nmea.sentences.base;

import java.io.Serializable;

import com.usda.fmsc.geospatial.nmea.exceptions.NmeaException;
import com.usda.fmsc.geospatial.nmea.NmeaIDs;

public class MultiSentence extends NmeaSentence implements Serializable {
    protected int totalMessageCount, messageCount;

    public MultiSentence(NmeaIDs.SentenceID sentenceID) {
        super(sentenceID);
    }

    public MultiSentence(NmeaIDs.SentenceID sentenceID, String nmea) {
        super(sentenceID, nmea);
    }

    @Override
    protected boolean parse(String nmea) {
        if (nmea != null) {
            if (this.rawNmea == null) {
                this.rawNmea = nmea;
            } else {
                rawNmea = String.format("%s\n%s", this.rawNmea, nmea);
            }

            valid = true;
        } else {
            valid = false;
        }

        return valid;
    }

    @Override
    public boolean isValid() {
        return super.isValid() && totalMessageCount > 0 && (totalMessageCount == messageCount);
    }

    protected void setTotalMessageCount(int count) {
        totalMessageCount = count;
    }

    protected void incrementMessageCount() {
        messageCount++;
    }

    public int getTotalMessageCount() {
        return totalMessageCount;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public boolean hasAllMessages() {
        return totalMessageCount > 0 && totalMessageCount == messageCount;
    }

    @Override
    public boolean isMultiString() {
        return true;
    }

    public class MismatchMessageCountException extends NmeaException {
        public MismatchMessageCountException() {
            super("Current GSV string total message count does not equal previous message's count number.");
        }
    }
}
