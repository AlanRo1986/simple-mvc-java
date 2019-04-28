package com.lanxinbase.system.utils;


import java.util.*;

public class CollectionUtils {
    public CollectionUtils() {
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static List arrayToList(Object source) {
        return Arrays.asList(ObjectUtils.toObjectArray(source));
    }

    public static <E> void mergeArrayIntoCollection(Object array, Collection<E> collection) {
        if (collection == null) {
            throw new IllegalArgumentException("Collection must not be null");
        } else {
            Object[] arr = ObjectUtils.toObjectArray(array);
            Object[] var3 = arr;
            int var4 = arr.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Object elem = var3[var5];
                collection.add((E) elem);
            }

        }
    }


    /**
     * 集合是否包含某个元素
     * @param iterator 集合对象
     * @param element 元素
     * @return true|false
     */
    public static boolean contains(Iterator<?> iterator, Object element) {
        if (iterator != null) {
            while(iterator.hasNext()) {
                Object candidate = iterator.next();
                if (ObjectUtils.nullSafeEquals(candidate, element)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 集合是否包含某个元素
     * @param enumeration 集合对象
     * @param element 元素
     * @return true|false
     */
    public static boolean contains(Enumeration<?> enumeration, Object element) {
        if (enumeration != null) {
            while(enumeration.hasMoreElements()) {
                Object candidate = enumeration.nextElement();
                if (ObjectUtils.nullSafeEquals(candidate, element)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 集合是否包含某个元素
     * @param collection 集合对象
     * @param element 元素
     * @return true|false
     */
    public static boolean containsInstance(Collection<?> collection, Object element) {
        if (collection != null) {
            for (Object candidate : collection) {
                if (candidate == element) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 集合是否包含某些集合元素
     * @param source 原集合对象
     * @param candidates 约束集合
     * @return true|false
     */
    public static boolean containsAny(Collection<?> source, Collection<?> candidates) {
        if (!isEmpty(source) && !isEmpty(candidates)) {
            Iterator var2 = candidates.iterator();

            Object candidate;
            do {
                if (!var2.hasNext()) {
                    return false;
                }

                candidate = var2.next();
            } while(!source.contains(candidate));

            return true;
        } else {
            return false;
        }
    }


    /**
     * 查找并匹配两个集合，如果找到相等的对象，则返回这个对象
     *
     * List<String> a1 = new ArrayList<>();
     * a1.add("1");
     * a1.add("2");
     * a1.add("3");
     *
     * List<String> a2 = new ArrayList<>();
     * a2.add("3");
     * a2.add("13");
     *
     * out(CollectionUtils.findFirstMatch(a1,a2)); => 3
     *
     * @param source 原数据集合对象
     * @param candidates 条件约束集合对象
     * @param <E> 集合对象类型
     * @return 失败返回null,成功返回E
     */
    public static <E> E findFirstMatch(Collection<?> source, Collection<E> candidates) {
        if (!isEmpty(source) && !isEmpty(candidates)) {
            Iterator var2 = candidates.iterator();

            Object candidate;
            do {
                if (!var2.hasNext()) {
                    return null;
                }

                candidate = var2.next();
            } while(!source.contains(candidate));

            return (E) candidate;
        } else {
            return null;
        }
    }

    /**
     * 获取集合类型
     * @param collection 集合对象
     * @return Class.name
     */
    public static Class<?> findCommonElementType(Collection<?> collection) {
        if (isEmpty(collection)) {
            return null;
        } else {
            Class<?> candidate = null;

            for (Object val : collection) {
                if (val != null) {
                    if (candidate == null) {
                        candidate = val.getClass();
                    } else if (candidate != val.getClass()) {
                        return null;
                    }
                }
            }

            return candidate;
        }
    }


}
