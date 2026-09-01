package com.freelance.motor.entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="messages")
@Getter
@Setter
public class Messages {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="channel")
    private String channel;

    @Column(name="recipient")
    private String recipient;

    @Column(name="template")
    private String template;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition="jsonb", name="variables")
    private Map<String, Double> variables;

    @Column(name="status_msg")
    @Enumerated(EnumType.STRING)
    private StatusEnum statusMsg;

    @Column(name="created_at")
    private LocalDateTime created_at;

    @Column(name="updated_at")
    private LocalDateTime updated_at;

    public Messages(){}

    public Messages(String channel, String recipient, String template,
            LocalDateTime created_at, LocalDateTime updated_at) {
        this.channel = channel;
        this.recipient = recipient;
        this.template = template;
        this.variables = new HashMap<>();
        this.statusMsg = StatusEnum.PENDING;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Messages{");
        sb.append("id=").append(id);
        sb.append(", channel=").append(channel);
        sb.append(", recipient=").append(recipient);
        sb.append(", template=").append(template);
        sb.append(", variables=").append(variables);
        sb.append(", statusMsg=").append(statusMsg);
        sb.append(", created_at=").append(created_at);
        sb.append(", updated_at=").append(updated_at);
        sb.append('}');
        return sb.toString();
    }

    public void setVariables(String truckId, double temperature){
        this.variables.put(truckId, temperature);
    }
}
