/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fram_index;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.io.FileFilter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.opencv.core.CvType;
import org.opencv.core.Size;
import org.opencv.videoio.VideoWriter;

/**
 *
 * @author ASUS
 */
public class fram_index {

    public static void main(String[] args) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        ArrayList<String> path = new ArrayList();
        JFrame frame1 = new JFrame("Brose video ");
        JButton btn = new JButton("Browse");
        btn.setBounds(150, 150, 100, 40);
        frame1.add(btn);
        frame1.setLayout(null);
        frame1.setLocationRelativeTo(null);
        frame1.setSize(300, 300);
        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser file = new JFileChooser();
                file.setCurrentDirectory(new File(System.getProperty("user.home")));
                //filtering files
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.video", "*.mp4");
                file.addChoosableFileFilter(filter);
                int res = file.showSaveDialog(null);
                //if the user clicks on save in Jfilechooser
                if (res == JFileChooser.APPROVE_OPTION) {
                    File selFile = file.getSelectedFile();
                    String name = selFile.getAbsolutePath();
                    path.add(name);
                }

            }
        });

        frame1.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

//////////////////////////////////////////////////////////////////////////////////////////////////////start window closing///////////////////////////////////////////////////////////
                if (!path.isEmpty()) {

                    ArrayList<Mat> merge_videos = new ArrayList();
                    ArrayList<Integer> pos = new ArrayList();
                    pos.add(0, Integer.SIZE);
                    pos.add(1, Integer.SIZE);
                    pos.add(2, Integer.SIZE);
                    //Create new VideoCapture object
                    VideoCapture camera = new VideoCapture(path.get(0));
                    int video_length = (int) camera.get(Videoio.CAP_PROP_FRAME_COUNT);
                    int frames_per_second = (int) camera.get(Videoio.CAP_PROP_FPS);
                    int frame_number = (int) camera.get(Videoio.CAP_PROP_POS_FRAMES);
                    // create a new frame
                    JFrame f_father = new JFrame("Video Editor");
                    // create a panel
                    JPanel panel = new JPanel();
                    // create a slider
                    JSlider slider = new JSlider(0, video_length / 10 - 1, 0);
                    // add slider to panel
                    panel.add(slider);
                    // add panel to frame
                    f_father.add(panel);
                    //Create a new JLabel object vidpanel
                    JLabel vidPanel = new JLabel();
                    //assign vidPanel to jframe
                    panel.add(vidPanel);
                    // set the size of frame
                    f_father.setSize(700, 700);
                    f_father.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
/////////////////////////////
                    JButton add_textwatermark = new JButton("Add Text");
                    add_textwatermark.setBounds(75, 50, 100, 50);
                    panel.add(add_textwatermark);
                    JButton add_imagewatermark = new JButton("Add Image");
                    add_imagewatermark.setBounds(75, 100, 100, 50);
                    panel.add(add_imagewatermark);
                    JButton start = new JButton("start pos");
                    JButton end = new JButton("end pos");
                    JButton delete = new JButton("Delete");
                    JButton move = new JButton("Move");
                    JButton merge = new JButton("merge video");
                    JButton photo = new JButton("merge photo");
                    panel.add(start);
                    panel.add(end);
                    panel.add(delete);
                    panel.add(move);
                    panel.add(merge);
                    panel.add(photo);
                    JButton save = new JButton("save");
                    save.setBounds(75, 100, 100, 50);
                    panel.add(save);
/////////////////////////////
                    f_father.setVisible(true);
                    //Create new MAT object
                    Mat frame = new Mat();
                    ///////ArrayList for save frames 
                    ArrayList<Mat> video = new ArrayList();
                    ///////store fram in Array
                    System.out.println("please wait while open video ...");
                    while (frame_number < video_length) {
                        //If next video frame is available
                        if (camera.read(frame)) {
                            if (frame_number % 10 == 0) {
                                Mat c = new Mat();
                                frame.copyTo(c);
                                video.add(c);
                            }
                        }
                        frame_number++;
                    }
                    System.out.println("video opened ");
/////////////////////////////////////////////////Button image action////////////////////////////////////////////////////
                    add_imagewatermark.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            JFrame f = new JFrame("Add Image Watermark");
                            JPanel panel = new JPanel();
                            JButton submit = new JButton("submit");
                            JButton b_file = new JButton(" chose file");
                            JSlider slider = new JSlider(0, 10, 3);
                            JLabel label = new JLabel();
                            label.setText("value of Slider is =" + slider.getValue());
                            // paint the ticks and tracks
                            slider.setPaintTrack(true);
                            slider.setPaintTicks(true);
                            slider.setPaintLabels(true);
                            // set spacing
                            slider.setMajorTickSpacing(2);
                            slider.setMinorTickSpacing(1);
                            // set Font for the slider
                            slider.setFont(new Font("Serif", Font.ITALIC, 20));
                            panel.add(slider);
                            panel.add(submit);
                            panel.add(label);
                            panel.add(b_file);
                            f.add(panel);
                            f.setSize(300, 300);
                            f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            f.setVisible(true);
                            JFileChooser file = new JFileChooser();
                            slider.addChangeListener(ee
                                    -> {
                                label.setText("value of Slider is =" + slider.getValue());
                            });
                            b_file.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {

                                    file.setCurrentDirectory(new File(System.getProperty("user.home")));
                                    //filtering files
                                    FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "png");
                                    file.addChoosableFileFilter(filter);
                                    int res = file.showSaveDialog(null);
                                    //if the user clicks on save in Jfilechooser
                                    if (res == JFileChooser.APPROVE_OPTION) {

                                    }

                                }
                            });
                            submit.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        File selFile = file.getSelectedFile();

                                        if (selFile != null) {
                                            String name = selFile.getAbsolutePath();
                                            float alpha = (float) slider.getValue() / 10;
                                            System.out.println(name);
                                            File file1 = new File(name);
                                            for (int i = 0; i < video.size(); i++) {
                                                BufferedImage a = Mat2BufferedImage(video.get(i));
                                                addImageWatermark(file1, a, alpha);
                                                BufferedImage2Mat(a).copyTo(video.get(i));
                                            }
                                            System.out.println("finish add Image");
                                            f.dispose();
                                        } else {
                                            System.out.println("file choser empty");
                                        }
                                    } catch (IOException e1) {
                                        System.out.println("catch error");
                                    }
                                }
                            });
                        }
                    });

////////////////////////////////////////////////Button text action////////////////////////////////////////////////////
                    add_textwatermark.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            JFrame f = new JFrame("Add Text Watermark");
                            JPanel panel = new JPanel();
                            JButton submit = new JButton("submit & chose color");
                            JTextArea text_area = new JTextArea(10, 10);
                            JSlider slider = new JSlider(0, 10, 3);
                            JLabel label = new JLabel();
                            label.setText("value of Slider is =" + slider.getValue());
                            // paint the ticks and tracks
                            slider.setPaintTrack(true);
                            slider.setPaintTicks(true);
                            slider.setPaintLabels(true);
                            // set spacing
                            slider.setMajorTickSpacing(2);
                            slider.setMinorTickSpacing(1);
                            // set Font for the slider
                            slider.setFont(new Font("Serif", Font.ITALIC, 20));
                            panel.add(slider);
                            panel.add(submit);
                            panel.add(label);
                            panel.add(text_area);
                            f.add(panel);
                            f.setSize(220, 350);
                            f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            f.setVisible(true);
                            slider.addChangeListener(ee
                                    -> {
                                label.setText("value of Slider is =" + slider.getValue());
                            });
                            submit.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    Color color = JColorChooser.showDialog(panel, "Choose", Color.ORANGE);
                                    System.out.println(text_area.getText());
                                    float alpha = (float) slider.getValue() / 10;
                                    System.out.println(alpha);
                                    for (int i = 0; i < video.size(); i++) {
                                        BufferedImage a = Mat2BufferedImage(video.get(i));
                                        addTextWatermark(text_area.getText(), a, alpha, color);
                                        BufferedImage2Mat(a).copyTo(video.get(i));
                                    }
                                    System.out.println("finish add Text");
                                    f.dispose();
                                }

                            });
                        }
                    });
////////////////////////////////////////////////Button save action////////////////////////////////////////////////////
                    save.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JFrame f = new JFrame("Save video");
                            JPanel panel = new JPanel();
                            JButton submit = new JButton("Import");
                            JButton b_file = new JButton(" chose folder");
                            JSlider slider = new JSlider(5, 100, frames_per_second);
                            JLabel label = new JLabel();

                            JTextArea width = new JTextArea(2, 5);
                            JTextArea height = new JTextArea(2, 5);
                            label.setText("value of FBS is =" + slider.getValue() + "\nwidth is : " + width.getText() + " \nheight is : " + height.getText());

                            // paint the ticks and tracks
                            slider.setPaintTrack(true);
                            slider.setPaintTicks(true);
                            slider.setPaintLabels(true);
                            // set spacing
                            slider.setMajorTickSpacing(25);
                            slider.setMinorTickSpacing(5);
                            // set Font for the slider
                            slider.setFont(new Font("Serif", Font.ITALIC, 20));
                            panel.add(slider);
                            panel.add(submit);
                            panel.add(label);
                            panel.add(b_file);
                            panel.add(width);
                            panel.add(height);
                            f.add(panel);
                            f.setSize(300, 350);
                            f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            f.setVisible(true);
                            JFileChooser file = new JFileChooser();
                            slider.addChangeListener(ee
                                    -> {
                                label.setText("value of FBS is =" + slider.getValue() + "\nwidth is : " + width.getText() + " \nheight is : " + height.getText());
                            });

                            b_file.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                                    file.setCurrentDirectory(new File(System.getProperty("user.home")));

                                    int res = file.showSaveDialog(null);
                                    //if the user clicks on save in Jfilechooser
                                    if (res == JFileChooser.APPROVE_OPTION) {

                                    }

                                }
                            });
                            submit.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {

                                    File selFile = file.getSelectedFile();
                                    if (selFile != null) {

                                        if (height.getText() != null && width.getText() != null) {

                                            for (int i = 0; i < video.size(); i++) {
                                                BufferedImage b = resize(Mat2BufferedImage(video.get(i)), Integer.parseInt(height.getText()), Integer.parseInt(width.getText()));
                                                BufferedImage2Mat(b).copyTo(video.get(i));

                                            }

                                        }

                                        String name = selFile.getAbsolutePath();
                                        System.out.println("path save : " + name);
                                        System.out.println("FBS : " + slider.getValue());
                                        //TAKE VALUE OF [5,1000]
                                        int randomNum = (int) (5 + (Math.random() * (1000 - 5)));
                                        String save_name = "save" + randomNum + ".mp4";

                                        merge_frame(video, name, save_name, slider.getValue());
                                        f_father.dispose();
                                        f.dispose();
                                    } else {
                                        System.out.println("folder choser empty");
                                    }
                                }
                            });
                        }
                    });
////////////////////////////////////////////////Button start action////////////////////////////////////////////////////
                    start.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            pos.set(0, slider.getValue());
                            System.out.println("Start pos is : " + slider.getValue());
                        }
                    });
////////////////////////////////////////////////Button end action////////////////////////////////////////////////////
                    end.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            pos.set(1, slider.getValue());
                            System.out.println("End  pos is : " + slider.getValue());
                        }
                    });
////////////////////////////////////////////////Button Move action////////////////////////////////////////////////////
                    move.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            pos.set(2, slider.getValue());
                            if (pos.get(0) < pos.get(1) && (pos.get(2) > pos.get(1) || pos.get(2) < pos.get(0))) {
                                System.out.println("Start  move ");
                                System.out.println("start pos is : " + pos.get(0));
                                System.out.println("end pos is : " + pos.get(1));
                                System.out.println("Move to pos is : " + pos.get(2));
                                ArrayList<Mat> copy = new ArrayList();
                                for (int i = pos.get(0); i < pos.get(1); i++) {
                                    Mat c = new Mat();
                                    video.get(i).copyTo(c);
                                    copy.add(c);
                                }
                                if (pos.get(2) > pos.get(1)) {
                                    for (int i = 0; i < copy.size(); i++) {
                                        Mat c = new Mat();
                                        copy.get(i).copyTo(c);
                                        video.add(pos.get(2), c);
                                    }
                                    for (int i = 0; i < copy.size(); i++) {
                                        video.remove(pos.get(0));
                                    }
                                } else {

                                    for (int i = 0; i < copy.size(); i++) {
                                        video.remove(pos.get(0));
                                    }

                                    for (int i = 0; i < copy.size(); i++) {
                                        Mat c = new Mat();
                                        copy.get(i).copyTo(c);
                                        video.add(pos.get(2), c);
                                    }

                                }
                                copy.clear();
                                System.out.println("Finish move ");

                            } else {
                                System.out.println("Wrong start and move  pos ");
                            }
                        }
                    });
////////////////////////////////////////////////Button cut action////////////////////////////////////////////////////
                    delete.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Start delete");
                            System.out.println("start pos is : " + pos.get(0));
                            System.out.println("end pos is : " + pos.get(1));
                            if (pos.get(0) < pos.get(1)) {
                                System.out.println("size before delete " + video.size());
                                for (int i = pos.get(0); i < pos.get(1); i++) {
                                    video.remove(i);
                                }
                                System.out.println("size after delete " + video.size());
                                slider.setMaximum(video.size() - 1);
                                System.out.println("End delete");
                            } else {
                                System.out.println("Wrong start pos ");
                            }
                        }
                    });
////////////////////////////////////////////////Button merge action////////////////////////////////////////////////////
                    merge.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            JFrame f = new JFrame("Merge videos");
                            JPanel panel = new JPanel();
                            JButton submit = new JButton("Import");
                            JButton b_file = new JButton(" chose file");
                            JSlider slider = new JSlider(5, 100, frames_per_second);
                            JLabel label = new JLabel();
                            label.setText("value of FBS is =" + slider.getValue());
                            // paint the ticks and tracks
                            slider.setPaintTrack(true);
                            slider.setPaintTicks(true);
                            slider.setPaintLabels(true);
                            // set spacing
                            slider.setMajorTickSpacing(25);
                            slider.setMinorTickSpacing(5);
                            // set Font for the slider
                            slider.setFont(new Font("Serif", Font.ITALIC, 20));
                            panel.add(label);
                            panel.add(slider);
                            panel.add(b_file);
                            panel.add(submit);
                            f.add(panel);
                            f.setSize(220, 300);
                            f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            f.setVisible(true);
                            slider.addChangeListener(ee
                                    -> {
                                label.setText("value of FBS is =" + slider.getValue());
                            });
                            b_file.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    JFileChooser file = new JFileChooser();
                                    file.setCurrentDirectory(new File(System.getProperty("user.home")));
                                    //filtering files
                                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Video Files", "*.mp4");
                                    file.addChoosableFileFilter(filter);
                                    int res = file.showSaveDialog(null);
                                    //if the user clicks on save in Jfilechooser
                                    if (res == JFileChooser.APPROVE_OPTION) {
                                        File selFile = file.getSelectedFile();
                                        String name = selFile.getAbsolutePath();
                                        VideoCapture cap1 = new VideoCapture(name);
                                        int video_length = (int) cap1.get(Videoio.CAP_PROP_FRAME_COUNT);
                                        int frames_per_second = (int) cap1.get(Videoio.CAP_PROP_FPS);
                                        int frame_number = (int) cap1.get(Videoio.CAP_PROP_POS_FRAMES);

                                        ///////store fram in Array
                                        System.out.println("please wait while add video ...");
                                        System.out.println("size before is : " + merge_videos.size());
                                        while (frame_number < video_length) {
                                            //If next video frame is available
                                            if (cap1.read(frame)) {
                                                if (frame_number % 10 == 0) {
                                                    Mat c = new Mat();
                                                    frame.copyTo(c);
                                                    merge_videos.add(c);
                                                }
                                            }
                                            frame_number++;
                                        }
                                        cap1.release();
                                        System.out.println("size is : " + merge_videos.size());
                                        System.out.println("video added ");

                                    }
                                }
                            });
                            submit.addActionListener(new ActionListener() {

                                public void actionPerformed(ActionEvent e) {
                                    JFileChooser file = new JFileChooser();
                                    file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                                    file.setCurrentDirectory(new File(System.getProperty("user.home")));

                                    int res = file.showSaveDialog(null);
                                    //if the user clicks on save in Jfilechooser
                                    if (res == JFileChooser.APPROVE_OPTION) {
                                        if (!merge_videos.isEmpty()) {
                                            File selFile = file.getSelectedFile();
                                            String name = selFile.getAbsolutePath();
                                            System.out.println("path save : " + name);
                                            //TAKE VALUE OF [5,1000]
                                            int randomNum = (int) (5 + (Math.random() * (1000 - 5)));
                                            String save_name = "save" + randomNum + ".mp4";

                                            merge_frame(merge_videos, name, save_name, slider.getValue());
                                            f.dispose();
                                        } else {
                                            System.out.println(" videos not added ");
                                        }
                                    }

                                }

                            });
                        }
                    });
////////////////////////////////////////////////Button photo action////////////////////////////////////////////////////
                    photo.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JFrame f = new JFrame("Merge photos to video");
                            JPanel panel = new JPanel();
                            JButton submit = new JButton("Import");
                            JButton b_file = new JButton(" Export");
                            JSlider slider = new JSlider(5, 100, frames_per_second);
                            JLabel label = new JLabel();
                            label.setText("value of FBS is =" + slider.getValue());
                            // paint the ticks and tracks
                            slider.setPaintTrack(true);
                            slider.setPaintTicks(true);
                            slider.setPaintLabels(true);
                            // set spacing
                            slider.setMajorTickSpacing(25);
                            slider.setMinorTickSpacing(5);
                            // set Font for the slider
                            slider.setFont(new Font("Serif", Font.ITALIC, 20));
                            panel.add(label);
                            panel.add(slider);
                            panel.add(b_file);
                            panel.add(submit);
                            f.add(panel);
                            f.setSize(220, 300);
                            f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            f.setVisible(true);
                            slider.addChangeListener(ee
                                    -> {
                                label.setText("value of FBS is =" + slider.getValue());
                            });
                            JFileChooser file1 = new JFileChooser();
                            b_file.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {

                                    file1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                                    file1.setCurrentDirectory(new File(System.getProperty("user.home")));

                                    int res = file1.showSaveDialog(null);
                                    //if the user clicks on save in Jfilechooser
                                    if (res == JFileChooser.APPROVE_OPTION) {

                                    }

                                }
                            });
                            submit.addActionListener(new ActionListener() {

                                public void actionPerformed(ActionEvent e) {
                                    JFileChooser file = new JFileChooser();
                                    file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                                    file.setCurrentDirectory(new File(System.getProperty("user.home")));

                                    int res = file.showSaveDialog(null);
                                    //if the user clicks on save in Jfilechooser
                                    if (res == JFileChooser.APPROVE_OPTION) {

                                        File selFile1 = file1.getSelectedFile();

                                        File selFile = file.getSelectedFile();

                                        if (selFile != null && selFile1 != null) {
                                            String name1 = selFile1.getAbsolutePath();
                                            String name = selFile.getAbsolutePath();
//TAKE VALUE OF [5,1000]
                                            int randomNum = (int) (5 + (Math.random() * (1000 - 5)));
                                            String save_name = "save" + randomNum + ".mp4";

                                            System.out.println("Start merge....");
                                            System.out.println("path Export image : " + name1);
                                            System.out.println("path save : " + name);

                                            merge_frame_from_file(name1, name, save_name, slider.getValue());
                                            System.out.println("End merge");
                                        } else {
                                            System.out.println("Not chose folder");
                                        }
                                    }

                                }

                            });

                        }
                    });
                    ////////action for slider
                    slider.addChangeListener(e
                            -> {
                        int threshold = slider.getValue();
                        //Create new image icon object and convert Mat to Buffered Image
                        ImageIcon image = new ImageIcon(Mat2BufferedImage(video.get(threshold)));
                        //Update the image in the vidPanel
                        vidPanel.setIcon(image);
                        //Update the vidPanel in the JFrame
                        vidPanel.repaint();

                    });

                } else {
                    System.out.println("you did not chose any video");
                }

//////////////////////////////////////////////////////////////////////////////////////////////////////end window closing///////////////////////////////////////////////////////////
            }
        });

    }

    /**
     * transfer Mat object to BufferedImage.
     *
     * @param m The Mat object want transfer to BufferedImage.
     * @return BufferedImage
     */
    public static BufferedImage Mat2BufferedImage(Mat m) {
        //Method converts a Mat to a Buffered Image
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }

    /**
     * Embeds a textual watermark over a source image to produce a watermarked
     * one.
     *
     * @param text The text to be embedded as watermark.
     * @param sourceImageFile The source image file.
     * @param destImageFile The output image file.
     * @param alpha The value of Transparency of text.
     */
    static void addTextWatermark(String text, BufferedImage sourceImage, float alpha, Color color) {

//            BufferedImage sourceImage = ImageIO.read(sourceImageFile);
        Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();

        // initializes necessary graphic properties
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alphaChannel);
        g2d.setColor(color);
        g2d.setFont(new Font("Arial", Font.BOLD, 64));
        FontMetrics fontMetrics = g2d.getFontMetrics();
        Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);

        // calculates the coordinate where the String is painted
        int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
        int centerY = sourceImage.getHeight() / 2;

        // paints the textual watermark
        g2d.drawString(text, centerX, centerY);
        //save image
//            ImageIO.write(sourceImage, "png", destImageFile);
        g2d.dispose();

        //System.out.println("The tex watermark is added to the image.");
    }

    /**
     * Embeds a image watermark over a source image to produce a watermarked
     * one.
     *
     * @param watermark The source imageWatermark file.
     * @param type The type imageWatermark after save it .
     * @param source The source image file.
     * @param destination The output image file.
     * @param alpha The value of Transparency of Watermark .
     */
    private static void addImageWatermark(File watermark, BufferedImage image, float alpha) throws IOException {
        //  BufferedImage image = ImageIO.read(source);
        BufferedImage overlay = resize(ImageIO.read(watermark), 150, 150);

        // determine image type and handle correct transparency
//        int imageType = "png".equalsIgnoreCase(type) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
//        BufferedImage watermarked = new BufferedImage(image.getWidth(), image.getHeight(), imageType);
        // initializes necessary graphic properties
        //Graphics2D w = (Graphics2D) watermarked.getGraphics();
        Graphics2D w = (Graphics2D) image.getGraphics();
        w.drawImage(image, 0, 0, null);
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        w.setComposite(alphaChannel);

        // calculates the coordinate where the String is painted
        int centerX = image.getWidth() / 2;
        int centerY = image.getHeight() / 2;

        // add text watermark to the image
        w.drawImage(overlay, centerX, centerY, null);
        //save image
        // ImageIO.write(watermarked, type, destination);
        w.dispose();
    }

    /**
     * Resize image a watermarked one.
     *
     * @param img The image want be Resize.
     * @param height The height image want it.
     * @param width The width image want it.
     */
    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    /**
     * save video which can change FBS for video.
     *
     * @param frame The ArrayList of Mat object.
     * @param folderPath_output The folder save video inside it.
     * @param save The name video want save it.
     * @param fbs The FBS for video want save it.
     */
    private static void merge_frame(ArrayList<Mat> frame, String folderPath_output, String save, int fbs) {
//        ArrayList<Mat> copy = new ArrayList();
//        for (int i = 0; i < frame.size(); i++) {
//            Mat newframe = new Mat();
//            copy.add(newframe);
//            frame.get(i).copyTo(copy.get(i));
//        }
        final String videoFileName = new File(folderPath_output, save).getAbsolutePath();
        int FOURCC_MJPG = VideoWriter.fourcc('x', '2', '6', '4');
        VideoWriter writer = null;
        for (int i = 0; i < frame.size(); i++) {
            //Mat frame = org.opencv.imgcodecs.Imgcodecs.imread(listOfFiles[i].getAbsolutePath());

            if (writer == null) {
                writer = new VideoWriter(videoFileName, FOURCC_MJPG, fbs, new Size(frame.get(i).width(), frame.get(i).height()), true);
                if (!writer.isOpened()) {
                    System.out.println("VideoCap.writeFromCameraToFolder() Bummer!!!!!");
                    break;
                }
            }

            System.out.println("Captured Frame Width " + frame.get(i).width() + " Height " + frame.get(i).height());
            writer.write(frame.get(i));
            // currentFrame = frame;
//            try {
//                Thread.currentThread().sleep(66);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            frame.get(i).release();
        }
        writer.release();
        System.out.println("Video Saved");
    }

    /**
     * transfer BufferedImage object to Mat.
     *
     * @param sourceImg The BufferedImage object want transfer to Mat.
     * @return Mat
     */
    public static Mat BufferedImage2Mat(BufferedImage sourceImg) {

        //long millis = System.currentTimeMillis();
        DataBuffer dataBuffer = sourceImg.getRaster().getDataBuffer();
        byte[] imgPixels = null;
        Mat imgMat = null;

        int width = sourceImg.getWidth();
        int height = sourceImg.getHeight();

        if (dataBuffer instanceof DataBufferByte) {
            imgPixels = ((DataBufferByte) dataBuffer).getData();
        }

        if (dataBuffer instanceof DataBufferInt) {

            int byteSize = width * height;
            imgPixels = new byte[byteSize * 3];

            int[] imgIntegerPixels = ((DataBufferInt) dataBuffer).getData();

            for (int p = 0; p < byteSize; p++) {
                imgPixels[p * 3 + 0] = (byte) ((imgIntegerPixels[p] & 0x00FF0000) >> 16);
                imgPixels[p * 3 + 1] = (byte) ((imgIntegerPixels[p] & 0x0000FF00) >> 8);
                imgPixels[p * 3 + 2] = (byte) (imgIntegerPixels[p] & 0x000000FF);
            }
        }

        if (imgPixels != null) {
            imgMat = new Mat(height, width, CvType.CV_8UC3);
            imgMat.put(0, 0, imgPixels);
        }

        // System.out.println("matify exec millis: " + (System.currentTimeMillis() - millis));
        return imgMat;
    }

    /**
     * transfer BufferedImage object to Mat.
     *
     * @param folderPath_input The file input which contain images.
     * @param folderPath_output The file which will import video on it.
     * @param save name video will save.
     * @param fbs FBS video.
     */
    public static void merge_frame_from_file(String folderPath_input, String folderPath_output, String save, int fbs) {

        final String videoFileName = new File(folderPath_output, save).getAbsolutePath();
        System.out.println("videoFileName: " + videoFileName);
        int FOURCC_MJPG = VideoWriter.fourcc('x', '2', '6', '4');
        File folder = new File(folderPath_input);
        File[] listOfFiles = folder.listFiles(new FileFilter() {

            public boolean accept(File pathname) {
                String path = pathname.getAbsolutePath();
                String extension = path.substring(path.lastIndexOf("."), path.length());
                return extension.contains("jpg");
            }
        });
        if (listOfFiles.length != 0) {
            VideoWriter writer = null;
            for (int i = 0; i < listOfFiles.length; i++) {
                Mat frame = org.opencv.imgcodecs.Imgcodecs.imread(listOfFiles[i].getAbsolutePath());

                if (writer == null) {
                    writer = new VideoWriter(videoFileName, FOURCC_MJPG, fbs, new Size(frame.width(), frame.height()), true);
                    if (!writer.isOpened()) {
                        System.out.println("VideoCap.writeFromCameraToFolder() Bummer!!!!!");
                        break;
                    }
                }

                //System.out.println("Captured Frame Width " + frame.width() + " Height " + frame.height());
                writer.write(frame);
                // currentFrame = frame;
//            try {
//                Thread.currentThread().sleep(66);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
                frame.release();
            }
            writer.release();
        } else {
            System.out.println(" -----------------------Folder empty not have any image---------------------------");
        }

    }

}
