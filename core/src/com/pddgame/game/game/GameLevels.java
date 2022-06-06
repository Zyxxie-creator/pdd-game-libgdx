package com.pddgame.game.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;
import com.pddgame.game.game.objects.Car;
import com.pddgame.game.game.objects.CarInterface;
import com.pddgame.game.game.objects.Pedestrian;
import com.pddgame.game.game.objects.RoadSign;
import com.pddgame.game.game.objects.TrafficLight;


/**
 *
 * Создание 30 уровней
 * Установка авто на уровне
 * Установка светофоров на уровне
 * Установка дорожных знаков на уровне
 *
 */
public class GameLevels {
    private StringBuilder rightAnswerDescr = new StringBuilder();
    private TiledMap roadMap;

    public TiledMap getRoadMap() {
        return this.roadMap;
    }

    public StringBuilder getRightAnswerDescr() {
        return this.rightAnswerDescr;
    }

    public void initLevel(World world, int i, Array<CarInterface> array, Array<TrafficLight> array2, Array<Array<Vector2>> array3, Array<Integer> array4, Array<RoadSign> array5) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        String str11;
        Array<TrafficLight> array6;
        String str12;
        String str13;
        String str14;
        String str15;
        String str17;
        String str18;
        String str19;
        String str20;
        String str21;
        Array<CarInterface> array8;
        String str27;
        String str28;
        String str29;
        String str30;
        Array<RoadSign> array10;
        String str33;
        String str34;
        String str35;
        String str36;
        Array<TrafficLight> array11;
        String str39;
        Array<RoadSign> array15;
        str = "Follow Path 2";
        str2 = "sign_3_2";
        str3 = str;
        str4 = "sign_1_1";
        if (i == 13) {
            Assets.instance.assetManager.load("maps/road_13.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_13.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 8, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 1, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 3"), 3, 2, 1, false, false));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_1"), 7, 0.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_2"), 7, 0.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_3"), 7, -90.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_4"), 7, -90.0f));
            this.rightAnswerDescr.append("При въезде на перекресток, на котором организовано круговое движение, обозначенный знаком 4.3 \"Круговое движение\", красный автомобиль обязан уступить дорогу всем транспортным средствам, движущимся по такому перекрестку п. 13.11(1) ПДД РФ.");
        }
            str6 = str4;
            str5 = "sign_3_1";
            str7 = str6;
            str11 = "sign_4_1";
            str8 = "sign_1_2";
            str9 = "sign_2_1";
            array6 = array2;
        if (i == 11) {
            Assets.instance.assetManager.load("maps/road_11.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_11.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 2, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 5, 2, -1, false, false));
            this.rightAnswerDescr.append("Водитель обязан уступить дорогу транспортному средству имеющему нанесенные на наружные поверхности специальные цветографические схемы, с включенными проблесковым маячком синего и красного цветов  и специальным звуковым сигналом. В данном случае первым перекресток проезжает зеленый автомобиль (Пункт 3.2 ПДД).");
        }
        if (i == 12) {
            Assets.instance.assetManager.load("maps/road_12.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_12.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 1, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 4, 2, -1, false, false));
            int[] iArr3 = {-1, 0, -1, -1, 0, -1, -1, 3, -1, -1, -1, -1, -1};
            int[] iArr4 = {-1, 1, -1, -1, 0, -1, -1, 0, -1, -1, -1, -1, -1};
            array6.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_l_1_1"), iArr3, false, 0.0f));
            array6.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_l_1_2"), iArr3, false, 180.0f));
            array6.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_l_2_1"), iArr4, false, -90.0f));
            array6.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_l_2_2"), iArr4, false, 90.0f));
            this.rightAnswerDescr.append("Остановка не далее стоп-линии обязательна при запрещающем сигнале светофора. Автобусу горит «зелёный», он без остановки выезжает на перекрёсток и перед поворотом налево останавливается, чтобы уступить дорогу легковому автомобилю, движущемуся прямо со встречного направления. («Горизонтальная разметка», пункты 6.13, 6.2, 13.3, 13.4 ПДД)");
        }
        else{
            str11 = "sign_4_1";
            str8 = "sign_1_2";
            str9 = "sign_2_1";
        }
            str15 = str7;
            str14 = str9;
            str13 = str8;
            str12 = "tr_lights";
            str18 = str3;
            str17 = "Follow Path 1";
            str20 = str12;
            str19 = "tr_light_1_1";
        if (i == 4) {
            Assets.instance.assetManager.load("maps/road_4.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_4.tmx");
            array8 = array;
            array8.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 5, 1, -1, true, true));
            str21 = str17;
            array8.add(new Car(world, this.roadMap.getLayers().get(str21), 2, 2, 0, false, false));
            this.rightAnswerDescr.append("Зеленый автомобиль обязан обеспечить беспрепятственный проезд перекрёстка «оперативнику» с включенными специальными сигналами независимо от направления его движения. Сделать это необходимо при движении в любом направлении. (Пункты 3.1, 3.2 ПДД).");
        } else {
            str21 = str17;
        }
        if (i == 9) {
            Assets.instance.assetManager.load("maps/road_9.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_9.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get(str18), 8, 1, -1, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get(str21), 2, 2, -1, false, false));
            array10 = array5;
            array10.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get(str15), 2, 0.0f));
            str29 = str15;
            array10.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get(str29), 1, 180.0f));
            str28 = str13;
            str27 = "tr_light_2_1";
            array10.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get(str28), 0, 180.0f));
            str30 = str14;
            array10.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get(str30), 1, 180.0f));
            array10.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get(str5), 2, -90.0f));
            array10.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get(str2), 1, 180.0f));
            array10.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get(str11), 0, 90.0f));
            array10.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_4_2"), 1, 180.0f));
            this.rightAnswerDescr.append("Перекрёсток неравнозначный. Транспортные средства, находящиеся на главной дороге, имеют преимущество. Но так как траектория движения легкового автомобиля не пересекается с грузовиком, зеленый автомобиль можете совершить движение через перекрёсток одновременно, при этом он должен учитывать преимущество грузовика, т.е. не создавать ему помех. («Дорожные знаки», пункты 1.2 термин «Уступить дорогу», 13.9 ПДД).");
        } else {
            array10 = array5;
            str27 = "tr_light_2_1";
            str30 = str14;
            str28 = str13;
            str29 = str15;

        }

        if (i == 8) {
            Assets.instance.assetManager.load("maps/road_8.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_8.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get(str18), 9, 1, 1, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get(str21), 2, 2, 0, false, false));
            int[] iArr9 = {-1, 0, -1, -1, 0, -1, -1, 3, -1, -1, -1, -1, -1};
            int[] iArr10 = {-1, 1, -1, -1, 0, -1, -1, 0, -1, -1, -1, -1, -1};
            str35 = str30;
            str34 = str28;
            array11 = array2;
            array11.add(new TrafficLight(this.roadMap.getLayers().get(str20).getObjects().get(str19), iArr9, false, 0.0f));
            str33 = str29;
            array11.add(new TrafficLight(this.roadMap.getLayers().get(str20).getObjects().get("tr_light_1_2"), iArr9, false, 180.0f));
            str36 = str27;
            array11.add(new TrafficLight(this.roadMap.getLayers().get(str20).getObjects().get(str36), iArr10, false, -90.0f));
            array11.add(new TrafficLight(this.roadMap.getLayers().get(str20).getObjects().get("tr_light_2_2"), iArr10, false, 90.0f));
            this.rightAnswerDescr.append(" Перекресток регулируется светофором, траектории движения трамвая и  зеленого автомобиля пересекаются. Находясь в равнозначных условиях трамвай имеет преимущество перед безрельсовыми Т.С. Зеленый автомобиль уступает дорогу трамваю. (Пункты 6.2, 13.6 ПДД).");
        } else {
            str35 = str30;
            str33 = str29;
            str34 = str28;
            array11 = array2;
        }
        if (i == 14) {
            Assets.instance.assetManager.load("maps/road_14.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_14.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 9, 1, -1, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 2, 2, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 3"), 3, 3, -1, false, false));
            this.rightAnswerDescr.append("Перекрёсток равнозначный. Трамвай в равнозначных условиях имеет преимущество перед безрельсовыми транспортными средствами. Проезжает первым. Водители зеленого и красного автомобилей руководствуются «правилом правой руки». У зеленого помехи справа нет, он проезжает перекрёсток, уступая только трамваю. (Пункт 13.11 ПДД).");
        }
        if (i == 15) {
            Assets.instance.assetManager.load("maps/road_15.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_15.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 4, 1, -1, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 1, 2, -1, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 3"), 3, 3, -1, false, false));
            str39 = str35;
            array10.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get(str39), 0, 0.0f));
            array10.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get(str33), 12, 0.0f));
            array10.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get(str34), 2, -135.0f));
            array10.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get(str35), 13, -135.0f));
            array10.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get(str5), 0, 135.0f));
            array10.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get(str2), 11, 135.0f));
            this.rightAnswerDescr.append("Перекрёсток неравнозначный. Главная дорога меняет направление. Транспортные средства, находящиеся на главной дороге, имеют преимущество, между собой руководствуются «правилом правой руки». У серебристого автомобиля помеха справа, он уступает автобусу. Красный легковой автомобиль проедет последним, так как находится на второстепенной дороге. (Пункты 13.9, 13.10, 13.11 ПДД).");
        }
        if (i == 7) {
            Assets.instance.assetManager.load("maps/road_7.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_7.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 4, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 1, 2, 1, false, false));
            int[] iArr13 = {-1, 0, -1, -1, 0, -1, -1, 3, -1, -1, -1, -1, -1};
            int[] iArr14 = {-1, 1, -1, -1, 0, -1, -1, 0, 36, -1, -1, -1, -1};
            array11.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_1_1"), iArr14, false, 0.0f));
            array11.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_1_2"), iArr14, false, 180.0f));
            array11.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_2_1"), iArr13, false, -90.0f));
            array11.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_2_2"), iArr13, false, 90.0f));
            this.rightAnswerDescr.append("«Правило правой руки» универсально. Оно не работает в двух случаях – один из случаев, когда ТС движется под дополнительную секцию, включенную одновременно с основным желтым или красным сигналом. В данной ситуации серебристый автомобиль обязан уступить дорогу всем ТС, движущимся с других направлений. Он обязан уступить дорогу автобусу. (Пункт 13.5 ПДД).");
        }

        if (i == 1) {
            Assets.instance.assetManager.load("maps/road_1.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_1.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 2, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 5, 2, 1, false, false));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_1"), 7, 0.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_2"), 7, 0.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_3"), 7, -90.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_4"), 7, -90.0f));
            this.rightAnswerDescr.append("Зеленый автомобиль имеет право проехать перекресток первым, поскольку на нем организовано круговое движение, и он обозначен знаком 4.3  \"Круговое движение\". При въезде на этот перекресток водитель автомобиля ДПС обязан уступить дорогу транспортным средствам, движущимся по такому перекрестку п. 13.11(1) ПДД РФ.");
        }
        if (i == 2) {
            Assets.instance.assetManager.load("maps/road_2.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_2.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 8, 1, -1, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 1, 2, 0, false, false));
            this.rightAnswerDescr.append("Перекрёсток равнозначный. Водители руководствуются «правилом правой руки». У легкового автомобиля помеха справа будет при движении прямо. Он уступает грузовику. (Пункт 13.11 ПДД).");
        }
        if (i == 3) {
            Assets.instance.assetManager.load("maps/road_3.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_3.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 8, 1, -1, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 1, 2, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 3"), 4, 2, 1, false, false));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_1_1"), 0, 0.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_1_2"), 1, 90.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_2_1"), 2, 180.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_2_2"), 1, 90.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_3_1"), 2, -90.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_3_2"), 1, 90.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_4_1"), 0, 90.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_4_2"), 1, 90.0f));
            this.rightAnswerDescr.append("Перекрёсток неравнозначный. Главная дорога меняет направление. Транспортные средства, находящиеся на главной дороге, имеют преимущество перед остальными независимо от их дальнейшего направления движения. Между собой руководствуются «правилом правой руки». Грузовик проезжает первым, зеленый автомобиль после него, красный  автомобиль - последним. (Пункты 13.9, 13.10, 13.11 ПДД).");
        }
        if (i == 10) {
            Assets.instance.assetManager.load("maps/road_10.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_10.tmx");
            array.add(new Pedestrian(world, this.roadMap.getLayers().get("Follow Path 1"), 10, 1, 0, false, false));
            array.add(new Pedestrian(world, this.roadMap.getLayers().get("Follow Path 2"), 11, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 3"), 91, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 4"), 8, 2, -1, false, false));
            this.rightAnswerDescr.append("Поворачивая налево на данном перекрестке, водитель грузовика должен уступить дорогу пешеходам, переходящим проезжую часть дороги, на которую он поворачивает п. 13.1 и велосипедисту, так как он двигается во встречном направлении.");
        }

        if (i == 5) {
            Assets.instance.assetManager.load("maps/road_5.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_5.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 9, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 2, 2, -1, false, false));
            array15 = array5;
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_1_1"), 41, 0.0f));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_1_2"), 41, 180.0f));
            this.rightAnswerDescr.append("Зеленый автомобиль и трамвай находятся в равнозначных условиях. Трамвай в таком случае имеет преимущество перед безрельсовыми транспортными средствами. Легковой автомобиль уступает дорогу трамваю. (Пункт 13.11 ПДД).");
        }
        else{
            array15 = array5;
        }
        if (i == 6) {
            Assets.instance.assetManager.load("maps/road_6.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_6.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 8, 1, 1, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 3, 2, -1, false, false));
            int[] iArr23 = {-1, 0, -1, -1, 0, -1, -1, 3, -1, -1, -1, -1, -1};
            int[] iArr24 = {-1, 1, -1, -1, 0, -1, -1, 0, -1, -1, -1, -1, -1};
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_1_1"), iArr23, false, 0.0f));
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_2_1"), iArr23, false, 180.0f));
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_3_1"), iArr24, false, -90.0f));
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_4_1"), iArr24, false, 90.0f));
            this.rightAnswerDescr.append("Перекрёсток регулируемый. Легковому и грузовому автомобилям разрешено движение. При повороте налево, развороте следует уступить дорогу транспортным средствам, движущимся со встречного направления прямо и направо. Легковой автомобиль уступает грузовику. (Пункты 13.3, 13.4 ПДД).");
        }
        if (i == 16) {
            Assets.instance.assetManager.load("maps/road_16.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_16.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 5, 1, 0, true, true));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 3, 2, -1, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 3"), 8, 3, 0, false, false));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_1_1"), 0, 0.0f));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_1_2"), 1, 0.0f));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_2_1"), 2, 180.0f));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_2_2"), 1, 0.0f));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_3_1"), 0, -90.0f));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_3_2"), 1, 0.0f));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_4_1"), 2, 90.0f));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_4_2"), 1, 0.0f));
            this.rightAnswerDescr.append("Бесспорно преимущество имеет «оперативник» с мигалкой синего цвета и включенной сиреной. Все остальные обязаны обеспечить ему беспрепятственный проезд перекрёстка. После его проезда проезжает красный автомобиль, так как находится на главной дороге и имеет преимущество перед грузовиком, находящимся на второстепенной дороге. (Пункты 3.1, 13.9 ПДД).");
        }
        if (i == 17) {
            Assets.instance.assetManager.load("maps/road_70.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_70.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 1, 1, -1, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 2, 2, 0, false, false));
            this.rightAnswerDescr.append("Перекрёсток равнозначный. Водители руководствуются «правилом правой руки». У серебристого автомобиля помехи справа нет, он проезжает перекрёсток первым. (Пункт 13.11 ПДД).");
        }
        if (i == 18) {
            Assets.instance.assetManager.load("maps/road_73.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_73.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 9, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 2, 2, -1, false, false));
            this.rightAnswerDescr.append("Перекрёсток равнозначный. Зеленый автомобиль и трамвай находятся в равнозначных условиях. В таком случае трамвай всегда имеет преимущество перед безрельсовыми транспортными средствами. (Пункт 13.11 ПДД).");
        }
        if (i == 19) {
            Assets.instance.assetManager.load("maps/road_74.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_74.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 8, 1, -1, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 3, 2, 1, false, false));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_4_1"), 2, 0.0f));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_3_1"), 2, 180.0f));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_2_1"), 0, -90.0f));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_1_1"), 0, 90.0f));
            this.rightAnswerDescr.append("Перекрёсток неравнозначный. Знак 2.4 обязывает красный автомобиль уступить дорогу транспортным средствам, движущимся по пересекаемой дороге. Только после того как водитель легкового авто убедится, что грузовой автомобиль действительно поворачивает налево и движение легкового авто не может создать ему помеху, он выезжает на перекрёсток, чтобы совершить поворот направо. (Пункты 1.2, 13.9 ПДД, «Дорожные знаки»).");
        }
        if (i == 20) {
            Assets.instance.assetManager.load("maps/road_76.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_76.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 1, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 2, 2, -1, false, false));
            this.rightAnswerDescr.append("Перекрёсток равнозначный. Водители безрельсовых транспортных средств между собой руководствуются «правилом правой руки», т.е. у кого помеха справа, тот и уступает дорогу. У зеленого автомобиля помеха справа, серебристый легковой автомобиль проезжает первым. (Пункт 13.11 ПДД).");
        }
        if (i == 21) {
            Assets.instance.assetManager.load("maps/road_77.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_77.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 1, 1, -1, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 3, 2, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 3"), 2, 3, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 4"), 8, 4, -1, false, false));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_1_1"), 0, 0.0f));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_1_2"), 1, 90.0f));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_2_1"), 2, 180.0f));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_2_2"), 1, 90.0f));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_3_1"), 2, -90.0f));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_3_2"), 1, 90.0f));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_4_1"), 0, 90.0f));
            array15.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_4_2"), 1, 90.0f));
            this.rightAnswerDescr.append("Перекрёсток неравнозначный. Главная дорога меняет направление. Первоначально проезжают перекрёсток транспортные средства, находящиеся на главной дороге. Они имеют преимущество. Между собой они руководствуются «правилом правой руки». У красного автомобиля помеха справа, он уступает только серебристому легковому автомобилю. После них, руководствуясь этим же правилом, проезжают перекрёсток транспортные средства, находящиеся на второстепенной дороге. Третьим перекресток проезжает зеленый автомобиль, последним грузовик (Пункты 13.9, 13.10, 13.11 ПДД).");
        }
        if (i == 22) {
            Assets.instance.assetManager.load("maps/road_78.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_78.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 9, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 9, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 3"), 1, 2, 1, false, false));
            int[] iArr33 = {-1, 0, -1, -1, 0, -1, -1, 3, -1, -1, -1, -1, -1};
            int[] iArr34 = {-1, 1, -1, -1, 0, -1, -1, 0, -1, -1, -1, -1, -1};
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_1_1"), iArr33, false, 0.0f));
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_1_2"), iArr33, false, 180.0f));
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_2_1"), iArr34, false, -90.0f));
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_2_2"), iArr34, false, 90.0f));
            this.rightAnswerDescr.append("При одновременном праве на движение трамваи имеют преимущество перед безрельсовыми транспортными средствами. (Пункты 6.10, 13.6 ПДД).");
        }
        if (i == 23) {
            Assets.instance.assetManager.load("maps/road_80.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_80.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 8, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 1, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 3"), 3, 2, 1, false, false));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_1"), 7, 0.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_2"), 7, 0.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_3"), 7, -90.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_4"), 7, -90.0f));
            this.rightAnswerDescr.append("При въезде на перекресток, на котором организовано круговое движение, обозначенный знаком 4.3 \"Круговое движение\", красный автомобиль обязан уступить дорогу всем транспортным средствам, движущимся по такому перекрестку п. 13.11(1) ПДД РФ.");
        }
        if (i == 24) {
            Assets.instance.assetManager.load("maps/road_83.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_83.tmx");
            array.add(new Pedestrian(world, this.roadMap.getLayers().get("Follow Path 1"), 10, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 1, 2, 1, false, false));
            int[] iArr37 = {-1, 0, -1, -1, 0, -1, -1, 3, -1, -1, -1, -1, -1};
            int[] iArr38 = {-1, 1, -1, -1, 0, -1, -1, 0, -1, -1, -1, -1, -1};
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_1_1"), iArr37, false, 0.0f));
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_1_2"), iArr37, false, 180.0f));
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_2_1"), iArr38, false, -90.0f));
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_2_2"), iArr38, false, 90.0f));
            this.rightAnswerDescr.append("При повороте направо по разрешающему сигналу светофора водитель должен уступить дорогу велосипедистам и пешеходам п. 13.1. ПДД РФ.");
        }
        if (i == 25) {
            Assets.instance.assetManager.load("maps/road_84.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_84.tmx");
            array.add(new Pedestrian(world, this.roadMap.getLayers().get("Follow Path 1"), 10, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 4, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 3"), 2, 2, -1, false, false));
            int[] iArr39 = {-1, 0, -1, -1, 0, -1, -1, 3, -1, -1, -1, -1, -1};
            int[] iArr40 = {-1, 1, -1, -1, 0, -1, -1, 0, -1, -1, -1, -1, -1};
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_1_1"), iArr39, false, 0.0f));
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_1_2"), iArr39, false, 180.0f));
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_2_1"), iArr40, false, -90.0f));
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_2_2"), iArr40, false, 90.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_1_2"), 0, 0.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_1_2"), 1, 0.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_2_1"), 2, 180.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_2_2"), 1, 0.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_3_1"), 0, -90.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_3_2"), 1, 0.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_4_1"), 2, 90.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_4_2"), 1, 0.0f));
            this.rightAnswerDescr.append("Перекресток, на который въезжает зеленый автомобиль, - регулируемый, и очередность движения на нем определяется НЕ знаками приоритета(2.1  8.13  и т.д), а сигналами светофора п. 6.15 и п. 13.3. Поворачивая налево, водитель зеленого автомобиля должен уступить дорогу автобусу, движущемуся со встречного направления прямо п. 13.4, а, завершая поворот,— и пешеходам, переходящим проезжую часть дороги, на которую он поворачивает п. 13.1.");
        }
        if (i == 26) {
            Assets.instance.assetManager.load("maps/road_86.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_86.tmx");
            array.add(new Pedestrian(world, this.roadMap.getLayers().get("Follow Path 1"), 10, 1, 0, false, false));
            array.add(new Pedestrian(world, this.roadMap.getLayers().get("Follow Path 2"), 11, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 3"), 91, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 4"), 8, 2, -1, false, false));
            this.rightAnswerDescr.append("Поворачивая налево на данном перекрестке, водитель грузовика должен уступить дорогу пешеходам, переходящим проезжую часть дороги, на которую он поворачивает п. 13.1 и велосипедисту, так как он двигается во встречном направлении.");
        }
        if (i == 27) {
            Assets.instance.assetManager.load("maps/road_87.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_87.tmx");
            array.add(new Pedestrian(world, this.roadMap.getLayers().get("Follow Path 1"), 10, 1, 0, false, false));
            array.add(new Pedestrian(world, this.roadMap.getLayers().get("Follow Path 2"), 11, 1, 0, false, false));
            array.add(new Pedestrian(world, this.roadMap.getLayers().get("Follow Path 3"), 11, 1, 0, false, false));
            array.add(new Pedestrian(world, this.roadMap.getLayers().get("Follow Path 4"), 10, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 5"), 8, 2, 1, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 6"), 1, 2, -1, false, false));
            this.rightAnswerDescr.append("Оба водителя при повороте на перекрестке должны уступить дорогу пешеходам п. 13.1 независимо от того, обозначено, место перехода разметкой или нет. При отсутствии пешеходного перехода Правила предписывают пешеходам переходить проезжую часть по линии тротуаров или обочин п. 4.3.");
        }
        if (i == 28) {
            Assets.instance.assetManager.load("maps/road_88.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_88.tmx");
            array.add(new Pedestrian(world, this.roadMap.getLayers().get("Follow Path 1"), 11, 1, 0, false, false));
            array.add(new Pedestrian(world, this.roadMap.getLayers().get("Follow Path 2"), 10, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 3"), 3, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 4"), 2, 3, -1, false, false));
            int[] iArr41 = {-1, 0, -1, -1, 0, -1, -1, 3, -1, -1, -1, -1, -1};
            int[] iArr42 = {-1, 1, -1, -1, 0, -1, -1, 0, -1, -1, -1, -1, -1};
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_1_1"), iArr41, false, 0.0f));
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_1_2"), iArr41, false, 180.0f));
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_2_1"), iArr42, false, -90.0f));
            array2.add(new TrafficLight(this.roadMap.getLayers().get("tr_lights").getObjects().get("tr_light_2_2"), iArr42, false, 90.0f));
            this.rightAnswerDescr.append("Поворачивая налево по разрешающему сигналу светофора, зеленый автомобиль обязан уступить дорогу встречному автомобилю, движущемуся прямо п. 13.4, а завершая поворот, также и пешеходам, переходящих проезжую часть дороги, на которую он поворачивает п. 13.1.");
        }
        if (i == 29) {
            Assets.instance.assetManager.load("maps/road_89.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_89.tmx");
            array.add(new Pedestrian(world, this.roadMap.getLayers().get("Follow Path 1"), 10, 1, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 3"), 2, 2, 1, false, false));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_1_1"), 0, 0.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_1_2"), 1, 90.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_2_1"), 2, 180.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_2_2"), 1, 90.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_3_1"), 2, -90.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_3_2"), 1, 90.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_4_1"), 0, 90.0f));
            array5.add(new RoadSign(this.roadMap.getLayers().get("road_signs").getObjects().get("sign_4_2"), 1, 90.0f));
            this.rightAnswerDescr.append("На любых перекрестках, т.е. на регулируемых и нерегулируемых, при повороте, как направо, так и налево, необходимо уступать дорогу пешеходам, переходящим проезжую часть дороги, на которую вы поворачиваете п. 13.1.");
        }
        if (i == 30) {
            Assets.instance.assetManager.load("maps/road_30.tmx", TiledMap.class);
            Assets.instance.assetManager.finishLoading();
            this.roadMap = (TiledMap) Assets.instance.assetManager.get("maps/road_30.tmx");
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 1"), 5, 1, 0, true, true));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 2"), 1, 2, 0, false, false));
            array.add(new Car(world, this.roadMap.getLayers().get("Follow Path 3"), 8, 3, -1, false, false));
            int[] iArr35 = {-1, 0, -1, -1, 0, -1, -1, 3, -1, -1, -1, -1, -1, -1, -1};
            int[] iArr36 = {-1, 1, -1, -1, 0, -1, -1, 0, -1, -1, -1, -1, -1, -1, -1};
            array6.add(new TrafficLight(this.roadMap.getLayers().get("TrLights 1"), iArr35, false, 0.0f));
            array6.add(new TrafficLight(this.roadMap.getLayers().get("TrLights 2"), iArr35, false, 180.0f));
            array6.add(new TrafficLight(this.roadMap.getLayers().get("TrLights 3"), iArr36, false, -90.0f));
            array6.add(new TrafficLight(this.roadMap.getLayers().get("TrLights 4"), iArr36, false, 90.0f));
            this.rightAnswerDescr.append("Перекресток регулируемый. Первым проедет «оперативник» со специальными сигналами, который может отступать от требований сигналов светофора. Другие водители должны обеспечить ему беспрепятственный проезд перекрёстка. Водитель грузовика обязан уступить серебристому автомобилю, т.е. транспортному средству, движущемуся прямо со встречного направления. (Пункты 3.1, 3.2,13.3,13.4 ПДД).");
        }

        for (int i2 = 0; i2 < array.size; i2++) {
            CarInterface carInterface = array.get(i2);
            array3.add(carInterface.getWayPoints());
            array4.add(Integer.valueOf(carInterface.getSequenceIndex()));
        }
    }
}
