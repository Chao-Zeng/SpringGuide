package com.charles.imsdk;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class HandshakeHandler extends ByteToMessageDecoder {
    public static final String name = "handshakeHandler";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("connect server " + ctx.channel().remoteAddress());
        super.channelActive(ctx);
        int version = 1;
        int magic = 123456;
        int cmd = 3;
        String body = "this is a test";
        int bodyLen = body.length();
        ByteBuf buf = ctx.alloc().buffer();
        buf.writeInt(version);
        buf.writeInt(magic);
        buf.writeInt(cmd);
        buf.writeInt(bodyLen);
        buf.writeBytes(body.getBytes());
        ctx.writeAndFlush(buf);
    }

    /**
     * Decode the from one {@link ByteBuf} to an other. This method will be called till either the input
     * {@link ByteBuf} has nothing to read when return from this method or till nothing was read from the input
     * {@link ByteBuf}.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs to
     * @param in  the {@link ByteBuf} from which to read data
     * @param out the {@link List} to which decoded messages should be added
     * @throws Exception is thrown if an error occurs
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 16) {
            return;
        }

        in.markReaderIndex();
        int version = in.readInt();
        int magic = in.readInt();
        int cmd = in.readInt();
        int bodyLen = in.readInt();
        System.out.println("version=" + version);
        System.out.println("magic=" + magic);
        System.out.println("cmd=" + cmd);
        System.out.println("bodyLen=" + bodyLen);

        if (in.readableBytes() < bodyLen) {
            in.resetReaderIndex();
            return;
        }

        byte[] body = new byte[bodyLen];
        in.readBytes(body);
        String content = new String(body);
        System.out.println("body:" + content);
    }
}
