package com.bukit.mygeosystem.ModeYellowPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 12/10/2015.
 */
public class YellowPagesReturn {


        private Summary summary;

        private List<Listing> listings = new ArrayList<Listing>();


        public Summary getSummary() {
            return summary;
        }


        public void setSummary(Summary summary) {
            this.summary = summary;
        }


        public List<Listing> getListings() {
            return listings;
        }


        public void setListings(List<Listing> listings) {
            this.listings = listings;
        }

    }

