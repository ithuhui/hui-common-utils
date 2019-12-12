package com.hui.common.dao.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <code>GsonUtils</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/8 12:04.
 *
 * @author Gary.Hu
 */
public enum GsonUtils {
    /**
     * init single
     */
    INSTANCE;

    @Getter
    private Gson gson;

    GsonUtils(){
        this.gson = new GsonBuilder()
                .disableHtmlEscaping()
                .registerTypeAdapter(new TypeToken<Map<String, Object>>() {}
                        .getType(), new GsonTypeAdapter())
                .registerTypeAdapter(List.class, new GsonTypeAdapter())
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .serializeSpecialFloatingPointValues()
                .create();
    }
    static class GsonTypeAdapter extends TypeAdapter<Object> {
        private final TypeAdapter<Object> delegate = new Gson().getAdapter(Object.class);

        @Override
        public Object read(JsonReader in) throws IOException {
            // 反序列化
            JsonToken token = in.peek();
            switch (token) {
                case BEGIN_ARRAY:
                    List<Object> list = new ArrayList<>();
                    in.beginArray();
                    while (in.hasNext()) {
                        list.add(read(in));
                    }
                    in.endArray();
                    return list;

                case BEGIN_OBJECT:
                    Map<String, Object> map = new HashMap<>();
                    in.beginObject();
                    while (in.hasNext()) {
                        map.put(in.nextName(), read(in));
                    }
                    in.endObject();
                    return map;

                case NUMBER:
                    //改写数字的处理逻辑，将数字值分为整型与浮点型。
                    double dbNum = in.nextDouble();

                    // 数字超过long的最大值，返回浮点类型
                    if (dbNum > Long.MAX_VALUE) {
                        return dbNum;
                    }

                    // 判断数字是否为整数值
                    if (dbNum == (int) dbNum) { //如果需要的话,这里还可以加上先尝试转byte,不过还是int通用一点
                        return (int) dbNum;
                    } else if (dbNum == (long) dbNum) {
                        return (long) dbNum;
                    } else {
                        return dbNum;
                    }

                case STRING:
                    return in.nextString();

                case BOOLEAN:
                    return in.nextBoolean();

                case NULL:
                    in.nextNull();
                    return null;

                default:
                    throw new IllegalStateException();
            }
        }

        @Override
        public void write(JsonWriter out, Object value) throws IOException {
            // 序列化不处理
            if (value == null) {
                out.nullValue();
                return;
            }
            delegate.write(out,value);
        }
    }

}
