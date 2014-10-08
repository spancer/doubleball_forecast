package com.coomia.bpneuro.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.coomia.bpneuro.neuro.NetAlgo;
import com.coomia.bpneuro.neuro.Neuron;

public class MainFrame extends JFrame
{
	// 设置网络类
	private SetNet ns;
	// BP算法类
	private NetAlgo na;
	// 算法线程
	private Thread nathread;

	public MainFrame()
	{
		super("神经网络之BP算法");
		setLocation(100, 100);
		setSize(720, 360);
		initWindow();// 初始化主界面
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		setVisible(true);

		// 初始化设计类
		ns = new SetNet();
		ns.setVisible(false);

		// 初始化BP算法类
		na = new NetAlgo();
		na.setVisible(true);
		add(na);
	}

	private void initWindow()
	{
		// 菜单条包括菜单和菜单项
		JMenuBar menuBar = new JMenuBar();
		// 以下文件菜单项
		JMenu fileMenu = new JMenu("文 件");
		JMenuItem setNet = new JMenuItem("设置网络");
		setNet.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				ns.setVisible(true);
			}
		});
		fileMenu.add(setNet);
		JMenuItem readlData = new JMenuItem("学习数据");
		readlData.addActionListener(new ImpoLearnData());
		fileMenu.add(readlData);
		JMenuItem readtData = new JMenuItem("泛化数据");
		readtData.addActionListener(new ImpoGenerData());
		fileMenu.add(readtData);
		JMenuItem saveNet = new JMenuItem("存储网络");
		saveNet.addActionListener(new saveNet());
		fileMenu.add(saveNet);
		JMenuItem loadNet = new JMenuItem("导入网络");
		loadNet.addActionListener(new loadNet());
		fileMenu.add(loadNet);
		JMenuItem itemExit = new JMenuItem("退 出");
		itemExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
		});
		fileMenu.add(itemExit);
		menuBar.add(fileMenu);

		// 以下运行菜单项
		JMenu runMenu = new JMenu("运 行");
		JMenu netLearn = new JMenu("学  习");
		JMenuItem newNetLearn = new JMenuItem("新网学习");
		netLearn.add(newNetLearn);
		newNetLearn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				na.setExecType(1);//设置算法运行方式是学习
				na.setNoNet();// 设置在新网学习
				na.setLayers(ns.getLayers());
				na.setParameter(ns.getParameter());
				nathread = new Thread(na);
				nathread.start();
			}
		});
		JMenuItem oldNetLearn = new JMenuItem("原网学习");
		netLearn.add(oldNetLearn);
		oldNetLearn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				na.setExecType(1);//设置算法运行方式是学习
				na.setHavaNet();// 设置在原网学习
				na.setLayers(ns.getLayers());
				na.setParameter(ns.getParameter());
				nathread = new Thread(na);
				nathread.start();
			}
		});
		JMenuItem netStop = new JMenuItem("暂停学习");
		netStop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				try
				{
					nathread.suspend();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		netLearn.add(netStop);
		JMenuItem netRestar = new JMenuItem("继续学习");
		netRestar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				try
				{
					nathread.resume();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		netLearn.add(netRestar);
		runMenu.add(netLearn);
		JMenuItem netRecall = new JMenuItem("回  想");
		runMenu.add(netRecall);
		netRecall.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				na.setExecType(2);//设置算法运行方式是回想
				nathread = new Thread(na);
				nathread.start();
			}
		});
		JMenuItem netGener = new JMenuItem("泛  化");
		runMenu.add(netGener);
		netGener.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				na.setExecType(3);//设置算法运行方式是泛化
				nathread = new Thread(na);
				nathread.start();
			}
		});
		menuBar.add(runMenu);
		JMenu helpMenu = new JMenu("帮 助");
		JMenuItem aboutItem = new JMenuItem("关  于");
		aboutItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				JOptionPane.showMessageDialog(getContentPane(),
						"版权：信研0802 张珩 2008000757" +"\n内容：神经网络反向误差传播算法！"
								+"\n邮箱：zhangheng1225@163.com" +"\n版本：V1.0.0",
						"关于", JOptionPane.DEFAULT_OPTION);
			}
		});
		helpMenu.add(aboutItem);
		menuBar.add(helpMenu);
		// 添加菜单条
		setJMenuBar(menuBar);
		setVisible(true);
	}

	// 导入学习数据监听器类
	private class ImpoLearnData implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			int[] netLayers = ns.getLayers();
			JFileChooser chooser = new JFileChooser();
			File file;
			chooser.setCurrentDirectory(new File("."));
			TextFileFilter filter = new TextFileFilter();
			chooser.setFileFilter(filter);
			chooser.showOpenDialog(null);
			file = chooser.getSelectedFile();
			try
			{
				DataInputStream din = new DataInputStream(new FileInputStream(
						file.toString()));
				Scanner in = new Scanner(din);
				int sum = netLayers[1] +netLayers[netLayers.length -1];
				int count = 0;
				while (in.hasNext())
				{
					for (int i = 0; i <sum; i++)
					{
						in.nextDouble();
					}
					count++;
				}
				din = new DataInputStream(new FileInputStream(file.toString()));
				in = new Scanner(din);
				double[][] learnData = new double[count][sum];
				for (int i = 0; i <count; i++)
				{
					for (int j = 0; j <sum; j++)
					{
						learnData[i][j] = in.nextDouble();
					}
				}
				na.setLearnData(learnData);
				// System.out.print("读入的学习集！\n");
				// 打印结果
				/**
				 * for (int i = 0; i <learnData.length; i++) { for (int j = 0; j
				 * <learnData[i].length; j++) { System.out.print(" "
				 * +learnData[i][j]); } System.out.println(""); }
				 */

			} catch (Exception e)
			{
				// 落入此处则网络结果与数据结构不匹配，给出提示！
				JOptionPane.showMessageDialog(MainFrame.this, e);
			}
		}
	}

	// 导入学习数据监听器类
	private class ImpoGenerData implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			int[] netLayers = ns.getLayers();
			JFileChooser chooser = new JFileChooser();
			File file;
			chooser.setCurrentDirectory(new File("."));
			TextFileFilter filter = new TextFileFilter();
			chooser.setFileFilter(filter);
			chooser.showOpenDialog(null);
			file = chooser.getSelectedFile();
			try
			{
				DataInputStream din = new DataInputStream(new FileInputStream(
						file.toString()));
				Scanner in = new Scanner(din);
				int sum = netLayers[1] +netLayers[netLayers.length -1];
				int count = 0;
				while (in.hasNext())
				{
					for (int i = 0; i <sum; i++)
					{
						in.nextDouble();
					}
					count++;
				}
				din = new DataInputStream(new FileInputStream(file.toString()));
				in = new Scanner(din);
				double[][] generData = new double[count][sum];
				for (int i = 0; i <count; i++)
				{
					for (int j = 0; j <sum; j++)
					{
						generData[i][j] = in.nextDouble();
					}
				}
				na.setGenarData(generData);
			} catch (Exception e)
			{
				// 落入此处则网络结果与数据结构不匹配，给出提示！
				JOptionPane.showMessageDialog(MainFrame.this, e);
			}
		}
	}

	private class saveNet implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			saveFile("txt");
		}

		public void saveFile(final String formatName)
		{
			JFileChooser chooser = new JFileChooser();
			TextFileFilter filter = new TextFileFilter();
			chooser.setFileFilter(filter);
			int r = chooser.showSaveDialog(MainFrame.this);
			if (r !=JFileChooser.APPROVE_OPTION)
				return;
			File f = chooser.getSelectedFile();
			try
			{
				PrintWriter out = new PrintWriter(new FileWriter(f));
				writeNet(out);
			} catch (IOException exception)
			{
				JOptionPane.showMessageDialog(MainFrame.this, exception);
			}
		}

		public void writeNet(PrintWriter out)
		{
			// 存储网络结构
			int[] netLayers = ns.getLayers();
			for (int i = 0; i <netLayers.length; i++)
			{
				out.print("" +netLayers[i] +" ");
			}
			out.println();
			out.println();
			// 保存网络参数
			double[] parameter = new double[4];
			parameter = ns.getParameter();
			for (int i = 0; i <4; i++)
			{
				out.print("" +parameter[i] +" ");
			}
			out.println();
			out.println();
			// 存储神经元之间的权值
			double[][][] netWeight = na.getWeight();
			for (int i = 0; i <netWeight.length; i++)
			{
				for (int j = 0; j <netWeight[i].length; j++)
				{
					for (int k = 0; k <netWeight[i][j].length; k++)
					{
						out.print("" +netWeight[i][j][k] +" ");
					}
					out.println();
				}
				out.println();
			}
			out.close();
		}
	}

	private class loadNet implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			JFileChooser chooser = new JFileChooser();
			File file;
			chooser.setCurrentDirectory(new File("."));
			TextFileFilter filter = new TextFileFilter();
			chooser.setFileFilter(filter);
			chooser.showOpenDialog(null);
			file = chooser.getSelectedFile();
			try
			{
				DataInputStream din = new DataInputStream(new FileInputStream(
						file.toString()));
				Scanner in = new Scanner(din);
				// 记录网络的层数
				int count = in.nextInt();
				int[] netLayers = new int[count +1];
				// 载入网络数据
				netLayers[0] = count;
				for (int i = 1; i <=count; i++)
				{
					netLayers[i] = in.nextInt();
				}
				// 载入网络参数
				double[] parameter = new double[4];
				for (int i = 0; i <4; i++)
				{
					parameter[i] = in.nextDouble();
				}
				// 初始化神经网络各层节点
				Neuron[][] neuroArray = new Neuron[netLayers[0]][];
				for (int i = 0; i <netLayers[0]; i++)
				{
					neuroArray[i] = new Neuron[netLayers[i +1]];
					for (int j = 0; j <neuroArray[i].length; j++)
					{
						neuroArray[i][j] = new Neuron();
					}
					System.out.println(neuroArray[i].length);
				}
				// 初始化神经网络连接权值
				// 分配权值表， 初始化神经网络连接权值
				double[][][] netWeight = new double[netLayers[0] -1][][];
				for (int i = 0; i <netLayers[0] -1; i++)
				{
					netWeight[i] = new double[netLayers[i +1]][netLayers[i +2]];
				}
				for (int i = 0; i <netWeight.length; i++)
				{
					for (int j = 0; j <netWeight[i].length; j++)
					{
						for (int k = 0; k <netWeight[i][j].length; k++)
						{
							netWeight[i][j][k] = in.nextDouble();
						}
					}
				}
				// 将网络层次写入ns网络设置类
				ns.setLayers(netLayers);
				// 将网络参数写入ns网络设置类
				ns.setParameter(parameter);
				// 将数据，设置网络数据在算法类中
				na.setLayers(netLayers);
				na.setParameter(parameter);
				na.setNeuros(neuroArray);
				na.setWeight(netWeight);

			} catch (Exception e)
			{
				// 落入此处则网络结果与数据结构不匹配，给出提示！
				JOptionPane.showMessageDialog(MainFrame.this, "文件中有错误！");
			}
		}
	}

	public static void main(String[] args)
	{
		new MainFrame();
	}
}

class TextFileFilter extends FileFilter
{
	public String getDescription()
	{
		return "文件";
	}

	public boolean accept(File f)
	{
		if (f.isDirectory())
			return true;
		String name = f.getName().toLowerCase();
		if (name.endsWith(".java") ||name.endsWith(".bat")
				||name.endsWith(".txt"))
			return true;
		else
			return false;
	}
}