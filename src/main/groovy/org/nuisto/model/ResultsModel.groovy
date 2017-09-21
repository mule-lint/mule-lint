package org.nuisto.model

class ResultsModel {
  Map<String, List<Infraction> > expectationFindings = [:]
  Map<String, Map<String, Integer> > aggregationTotals = [:]
}