package sabin_rains;
import java.awt.*;
import java.util.Map;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

import sabin_rains.*;

/*
 * ActivePanel类，用于绘制鼠标悬停时的反应
 * 主要功能：
 * 1. 绘制鼠标悬停时的反应
 * 2. 绘制可以翻转的点
 * 
 * 函数说明：
 * 1. paintHexagons: 绘制鼠标悬停时的反应
 * 2. clearHexagons: 清除鼠标悬停时的反应
 * 3. paintReaction: 绘制可以翻转的点
 * 4. canInvertpoint: 获取可以翻转的点
 * 5. sortNeighborsClockwise: 将邻居点按顺时针排序
 */

public class ActivePanel extends JPanel{
    private Map<Point, StringPair> pointStatusMap;
    private HexagonPanel hexPanel;
    private PointPanel pointPanel;
    private String pointColor;
    private int mouseOnX = -1;
    private int mouseOnY = -1;
    // 类构造函数
    public ActivePanel(Map<Point, StringPair> pointStatusMap, List<Point> flagPoints) {
        setOpaque(false);
        this.pointStatusMap = pointStatusMap;
    }
    // 给pointStatusMap赋值
    public void setStatusMap(Map<Point, StringPair> pointStatusMap) {
        this.pointStatusMap = pointStatusMap;
    }
    // 绘制鼠标悬停时的反应
    public void paintHexagons(HexagonPanel hexPanel, PointPanel pointPanel, int x, int y, String pointColor){
        this.hexPanel = hexPanel;
        this.pointPanel = pointPanel;
        this.pointColor = pointColor;
        this.mouseOnX = x;
        this.mouseOnY = y;
        repaint();
    }
    // 清除鼠标悬停时的反应
    public void clearHexagons(){
        this.mouseOnX = -1;
        this.mouseOnY = -1;
        repaint();
    }

    // 用于将邻居点按顺时针排序的函数
    public List<Point> sortNeighborsClockwise(Point center, List<Point> neighbors, Point starPoint) {
        neighbors.sort(new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                // 计算两点与中心点的夹角
                double angle1 = Math.atan2(p1.y - center.y, p1.x - center.x);
                double angle2 = Math.atan2(p2.y - center.y, p2.x - center.x);
                
                // 比较两个夹角的大小
                return Double.compare(angle1, angle2);
            }
        });
        int startIndex = neighbors.indexOf(starPoint);
        for (int i = 0; i < startIndex; i++) {
            Point temp = neighbors.get(0);
            neighbors.remove(0);
            neighbors.add(temp);
        }
        return neighbors;
    }

    // 获取可以翻转的点
    public List<Point> canInvertpoint(){
        List<Point> poss_to_invert = new ArrayList<>();
        Point centerPoint = new Point(this.mouseOnX, this.mouseOnY);
        if (!this.pointStatusMap.get(centerPoint).matches("empty", "can_be_reach")) {
            return poss_to_invert;
        }
        String thisColor = this.pointColor;
        String anothorColor = thisColor.equals("red") ? "blue" : "red";
        // 找到中心点的邻居点
        List<Point> pointsNeighbour = pointPanel.findNeighbors(centerPoint);
        for (Point pN : pointsNeighbour) {
            List<Point> pNneighbours = this.sortNeighborsClockwise(pN, pointPanel.findNeighbors(pN), centerPoint);
            pNneighbours.remove(centerPoint);
            // clocklist 是一个可以翻转的顺时针的点的列表
            List<Point> clockList = new ArrayList<>();
            for (Point pNn : pNneighbours) {
                StringPair status = this.pointStatusMap.get(pNn);
                if (status.matches1st(anothorColor)) {
                    clockList.add(pNn);
                    if (clockList.size() == 5) {
                        poss_to_invert.addAll(clockList);
                        break;
                    }
                }
                else if (status.matches1st(thisColor)) {
                    poss_to_invert.addAll(clockList);
                    break;
                }
                else if (status.matches1st("empty")) {
                    clockList.clear();
                    break;
                }
                else {
                    break;
                }
            }
            // interclockList 是一个可以翻转的逆时针的点的列表
            List<Point> interclockList = new ArrayList<>();
            for (int i = pNneighbours.size() - 1; i >= 0; i--) {
                Point pNn = pNneighbours.get(i);
                StringPair status = this.pointStatusMap.get(pNn);
                if (status.matches1st(anothorColor)) {
                    interclockList.add(pNn);
                    if (interclockList.size() == 5) {
                        poss_to_invert.addAll(interclockList);
                        break;
                    }
                }
                else if (status.matches1st(thisColor)) {
                    poss_to_invert.addAll(interclockList);
                    break;
                }
                else if (status.matches1st("empty")) {
                    interclockList.clear();
                    break;
                }
                else {
                    break;
                }
            }
        }
        return poss_to_invert; 
    }
    // 绘制可以翻转的点
    public void paintReaction(Graphics g2){
        Graphics2D g = (Graphics2D) g2;
        g.setStroke(new BasicStroke(3)); // Set the line thickness to 3
        if (this.mouseOnX == -1 || this.mouseOnY == -1) {
            return;
        }
        StringPair mouseOnStatus = this.pointStatusMap.get(new Point(this.mouseOnX, this.mouseOnY));
        if (mouseOnStatus.matches("empty", "can_be_reach")) {
            Polygon hexagon = this.hexPanel.generateHexagon(this.mouseOnX, this.mouseOnY);
            g.setColor(this.pointColor.equals("red") ? Color.RED : Color.BLUE);
            g.drawPolygon(hexagon);
        }
        for (Point p : canInvertpoint()) {
            Polygon hexagon = hexPanel.generateHexagon(p.x, p.y);
            g.setColor(Color.WHITE);
            g.drawPolygon(hexagon);
        }
        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintReaction(g);
    }
    
}
