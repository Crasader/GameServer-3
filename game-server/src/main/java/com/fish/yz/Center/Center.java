package com.fish.yz.Center;

import com.fish.yz.Entity.ServerEntity;
import com.fish.yz.Repo;
import com.fish.yz.protobuf.Protocol;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fishman on 17/12/2016.
 * Center 的基类
 */
public class Center extends ServerEntity {
    private List<Protocol.EntityMailbox> cells = new ArrayList<Protocol.EntityMailbox>();

    public Center(){
        this(ObjectId.get());
    }

    public Center(ObjectId entityId){
        super(entityId);
    }

    public void regCell(Document doc) {
        String id = doc.getString("id");
        String ip = doc.getString("ip");
        int port = doc.getInteger("port");

        Protocol.ServerInfo.Builder sb = Protocol.ServerInfo.newBuilder();
        sb.setIp(ByteString.copyFromUtf8(ip));
        sb.setPort(port);

        Protocol.EntityMailbox.Builder mb = Protocol.EntityMailbox.newBuilder();
        mb.setEntityid(ByteString.copyFromUtf8(new ObjectId(id).toString()));
        mb.setServerinfo(sb.build());

        Protocol.EntityMailbox mailbox = mb.build();
        if (!this.cells.contains(mailbox)){
            this.cells.add(mailbox);
            System.out.println("register cell " + mailbox);
        }
    }


}