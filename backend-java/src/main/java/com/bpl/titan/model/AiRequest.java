package com.bpl.titan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

// =============================================
// REQUEST: Frontend se aane wala data
// =============================================
public class AiRequest {
    private String query;
    private String teamName;
    private String role;
    private String chatHistory;
    private String auctionContext;

    // Getters & Setters
    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getChatHistory() { return chatHistory; }
    public void setChatHistory(String chatHistory) { this.chatHistory = chatHistory; }

    public String getAuctionContext() { return auctionContext; }
    public void setAuctionContext(String auctionContext) { this.auctionContext = auctionContext; }
}
