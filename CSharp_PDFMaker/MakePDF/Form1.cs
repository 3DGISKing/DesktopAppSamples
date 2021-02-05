using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.OleDb;
using System.Drawing;
using System.Drawing.Printing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

using PdfSharp.Drawing;
using PdfSharp.Pdf;

delegate double ConvertValueToPixel(double value); 

namespace MakePDF2
{
    public partial class Form1 : Form
    {
        private OleDbConnection myConnection = new OleDbConnection();
        private OleDbDataReader dr;
        string provider;
        string docName = "";
        string pdfPath = "";

        private PrintDocument printDoc = new PrintDocument();
        Bitmap memoryImage1;
        Bitmap memoryImage2;     
        public Form1()
        {
            InitializeComponent();           
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            printDoc.DefaultPageSettings.PaperSize = new PaperSize("My custom size", 827, 1169);
            printDoc.DefaultPageSettings.PrinterResolution =
                printDoc.PrinterSettings.PrinterResolutions[1]; //medium
            printDoc.PrintPage += new PrintPageEventHandler(printDocument1_PrintPage);

            provider = "Provider=Microsoft.ACE.OLEDB.12.0;Data Source =";
            myConnection.ConnectionString = provider + "document.accdb";

            //Read document name and insert to listbox
            string query = "select [doc_name] from doctable";
            try
            {
                myConnection.Open();
                OleDbCommand cmd = new OleDbCommand(query, myConnection);
                dr = cmd.ExecuteReader();
                while(dr.Read()){
                    listBox1.Items.Add( dr[0].ToString());    
                }               
             
                myConnection.Close();
            }
            catch
            {
                MessageBox.Show("Error: There was a problem with the database.", "error");
            }

            MakeNewDocName();

            pdfPath = lblFolderPath.Text = System.Environment.GetFolderPath(Environment.SpecialFolder.MyDocuments);
        }


        private void printDocument1_PrintPage(System.Object sender,
           System.Drawing.Printing.PrintPageEventArgs e)
        {
            e.Graphics.DrawImage(memoryImage1, 70, 110);
            e.Graphics.DrawImage(memoryImage2, 70, 798);
        }

        private void button1_Click(object sender, EventArgs e)
        {
            SetColorTextBg(Color.FromArgb(255, 255, 255));          
            CaptureScreen();

            printDialog1.Document = printDoc;     

            DialogResult result = printDialog1.ShowDialog();
            if (result == DialogResult.OK)
            {
                printDoc.Print();
            }
        }

        private void button6_Click(object sender, EventArgs e)
        {
            //SetColorTextBg(Color.FromArgb(255, 255, 255));
            //CaptureScreen();
            //printDoc.DefaultPageSettings.PrinterSettings.PrinterName = "Microsoft Print to PDF";
            //printDoc.DefaultPageSettings.PrinterSettings.PrintToFile = true;
            //printDoc.DefaultPageSettings.PrinterSettings.PrintFileName = lblFolderPath.Text;
            //printDoc.Print();

            SetColorTextBg(Color.FromArgb(255, 255, 255));
            CaptureScreen();
            try
            {
                PdfDocument pdfDocument = null;
                pdfDocument = new PdfDocument();

                Bitmap pageBitmap = new Bitmap(827, 1259);
                Graphics pageGraphics = Graphics.FromImage(pageBitmap);

                pageGraphics.DrawImage(memoryImage1, 70, 110);
                pageGraphics.DrawImage(memoryImage2, 70, 888);

                PdfPage pdfPage = new PdfPage();

                pdfPage.Width = XUnit.FromPoint(595.27184);
                pdfPage.Height = XUnit.FromPoint(931.88446);

                pdfDocument.AddPage(pdfPage);
                XImage xImage = XImage.FromGdiPlusImage(pageBitmap);
                XGraphics xGraphics = XGraphics.FromPdfPage(pdfPage);
                xGraphics.DrawImage(xImage, 0, 0);
                pdfDocument.Save(lblFolderPath.Text);

                MessageBox.Show("The file '"+ docName + ".pdf" + "' has been made.", "Note");
            }
            catch
            {                
                MessageBox.Show("The file '"+ docName + ".pdf" + "' can not be overwritten because it is open.Please close the file and try again.", "Note");
            }            
        }

        private void CaptureScreen()
        {
            Graphics myGraphics1 = this.CreateGraphics();
                      
            Size s1 = new Size(660, 790);

            memoryImage1 = new Bitmap(s1.Width, s1.Height, myGraphics1);
            Graphics memoryGraphics1 = Graphics.FromImage(memoryImage1);
            memoryGraphics1.CopyFromScreen(this.Location.X + 36, this.Location.Y + 31 + 38, 0, 0, s1);

            Graphics myGraphics2 = this.CreateGraphics();
                     
            Size s2 = new Size(660, 251);
            memoryImage2 = new Bitmap(s2.Width, s2.Height, myGraphics2);

            Graphics memoryGraphics2 = Graphics.FromImage(memoryImage2);
            memoryGraphics2.CopyFromScreen(this.Location.X + 702, this.Location.Y + 31 + 38, 0, 0, s2);
        }

        private void SetColorTextBg(Color color)
        {
            textBox1.BackColor = color; textBox2.BackColor = color;
            textBox3.BackColor = color; textBox4.BackColor = color;
            textBox5.BackColor = color; textBox6.BackColor = color;
            textBox7.BackColor = color; textBox8.BackColor = color;
            textBox9.BackColor = color; textBox10.BackColor = color;
            textBox11.BackColor = color; textBox12.BackColor = color;
            textBox13.BackColor = color; textBox14.BackColor = color;
            textBox15.BackColor = color; textBox16.BackColor = color;
            textBox17.BackColor = color; textBox18.BackColor = color;
            textBox19.BackColor = color; textBox20.BackColor = color;
            textBox21.BackColor = color; textBox22.BackColor = color;
            textBox23.BackColor = color; textBox24.BackColor = color;
            textBox25.BackColor = color; textBox26.BackColor = color;
            textBox27.BackColor = color; textBox100.BackColor = color;  

            panel1.Update(); panel20.Update();
        } 

        private void drawGrid(object sender, PaintEventArgs e, double gridWidth, double gridHeight)
        {
            Graphics gr = e.Graphics;
            Point p1, p2;
            Pen darkGray2Pen = new Pen(Color.DarkGray, 1.0f);
            Pen dimGray2Pen = new Pen(Color.DimGray, 1.0f);

            //draw grid

            int i = 0;
            double y = 0;

            double left = 0, right = paneGraph.Width - 1;
            double height = paneGraph.Height - 1;
            int count = (int) (height / gridHeight);

            count++;
            // draw horizontal grid axises starting from top

            for (i = 0; i < count; i++)
            {
                p1 = new Point((int)left, (int)y);
                p2 = new Point((int)right, (int)y);

                if ((i % 5) == 0)
                    gr.DrawLine(dimGray2Pen, p1, p2);
                else
                    gr.DrawLine(darkGray2Pen, p1, p2);

                y += gridHeight;
            }


            // draw vertical grid axises

            double x = 0;
                       
            int bottom = 0, top = paneGraph.Height - 1;
            double width = paneGraph.Width - 1;
            count = (int)(width / gridWidth);
            count++;
            
            for (i = 0; i < count; i++)
            {
                p1 = new Point((int)x, bottom);
                p2 = new Point((int)x, top);

                if ((i % 5) == 0)
                    gr.DrawLine(dimGray2Pen, p1, p2);
                else
                    gr.DrawLine(darkGray2Pen, p1, p2);

                x += gridWidth;
            }
        }

        private void drawRedCurve(object sender, PaintEventArgs e, ConvertValueToPixel xMapFunc, ConvertValueToPixel yMapFunc)
        {
            int i = 0;
            Point p1 = new Point(), p2 = new Point();
            Graphics gr = e.Graphics;

            // Draw Graph
            // red curve
            // y axis is Angle smoll bush   
            double x1, y1;

            // fist we find first non empty text pair

            for (i = 15; i > 0; i--)
            {
                TextBox tbx1 = (Controls.Find("textBoxX1_" + i.ToString(), true)[0] as TextBox);
                TextBox tby11 = (Controls.Find("textBoxY11_" + i.ToString(), true)[0] as TextBox);

                if (tby11.Text == "" || tbx1.Text == "")
                    continue;

                x1 = Convert.ToSingle(tbx1.Text);
                y1 = Convert.ToSingle(tby11.Text);

                x1 = xMapFunc(x1);
                y1 = yMapFunc(y1);

                p1 = new Point((int)x1, (int)(y1));

                break;
            }
            
            for (int j = i - 1; j > 0; j--)
            {
                TextBox tbx1 = (Controls.Find("textBoxX1_" + j.ToString(), true)[0] as TextBox);
                TextBox tby11 = (Controls.Find("textBoxY11_" + j.ToString(), true)[0] as TextBox);

                if (tby11.Text == "" || tbx1.Text == "")
                    continue;

                double x2 = Convert.ToDouble(tbx1.Text);
                double y2 = Convert.ToDouble(tby11.Text);

                x2 = xMapFunc(x2);
                y2 = yMapFunc(y2);

                p2 = new Point((int)x2, (int)(y2));
                gr.DrawLine(new Pen(Color.Red, 2.0f), p1, p2);

                gr.FillEllipse(Brushes.Black, (int)p1.X - 2, p1.Y - 2, 4, 4);
                gr.FillEllipse(Brushes.Black, (int)p2.X - 2, p2.Y - 2, 4, 4);

                p1 = p2;
            }
        }
        
        private void drawGreenCurve(object sender, PaintEventArgs e, ConvertValueToPixel xMapFunc, ConvertValueToPixel yMapFunc)
        {
            int i = 0;
            Point p1 = new Point(), p2 = new Point();
            Graphics gr = e.Graphics;
            double x1, y1;

            //green curve               
            //  y axis is Angle large bush   
            for (i = 13; i > 0; i--)
            {
                TextBox tbx1 = (Controls.Find("textBoxX1_" + i.ToString(), true)[0] as TextBox);
                TextBox tby12 = (Controls.Find("textBoxY12_" + i.ToString(), true)[0] as TextBox);

                if (tby12.Text == "" || tbx1.Text == "")
                    continue;

                x1 = Convert.ToSingle(tbx1.Text);
                y1 = Convert.ToSingle(tby12.Text);

                x1 = xMapFunc(x1);
                y1 = yMapFunc(y1);

                p1 = new Point((int)x1, (int)(y1));
                break;
            }

            for (int j = i - 1; j > 0; j--)
            {
                TextBox tbx1 = (Controls.Find("textBoxX1_" + j.ToString(), true)[0] as TextBox);
                TextBox tby12 = (Controls.Find("textBoxY12_" + j.ToString(), true)[0] as TextBox);
                if (tby12.Text == "" || tbx1.Text == "") continue;

                double x2 = Convert.ToDouble(tbx1.Text);
                double y2 = Convert.ToDouble(tby12.Text);

                x2 = xMapFunc(x2);
                y2 = yMapFunc(y2);

                p2 = new Point((int)x2, (int)(y2));
                gr.DrawLine(new Pen(Color.Green, 2.0f), p1, p2);

                gr.FillEllipse(Brushes.Black, (int)p1.X - 2, p1.Y - 2, 4, 4);
                gr.FillEllipse(Brushes.Black, (int)p2.X - 2, p2.Y - 2, 4, 4);

                p1 = p2;
            }
        }

        private void drawBlueCurve(object sender, PaintEventArgs e, ConvertValueToPixel xMapFunc, ConvertValueToPixel yMapFunc)
        {
            double top = paneGraph.Height - 1;

            int i = 0;
            Point p1 = new Point(), p2 = new Point();
            Graphics gr = e.Graphics;
            double x1, y1;

            //blue curve               
            for (i = 13; i > 0; i--)
            {
                TextBox tbx2 = (Controls.Find("textBoxX2_" + i.ToString(), true)[0] as TextBox);
                TextBox tby21 = (Controls.Find("textBoxY21_" + i.ToString(), true)[0] as TextBox);

                if (tby21.Text == "" || tbx2.Text == "")
                    continue;

                x1 = Convert.ToSingle(tbx2.Text);
                y1 = Convert.ToSingle(tby21.Text);

                x1 = xMapFunc(x1);
                y1 = yMapFunc(y1);

                p1 = new Point((int)x1, (int)(y1));
                break;
            }

            for (int j = i - 1; j > 0; j--)
            {
                TextBox tbx2 = (Controls.Find("textBoxX2_" + j.ToString(), true)[0] as TextBox);
                TextBox tby21 = (Controls.Find("textBoxY21_" + j.ToString(), true)[0] as TextBox);

                if (tby21.Text == "" || tbx2.Text == "")
                    continue;

                double x2 = Convert.ToDouble(tbx2.Text);
                double y2 = Convert.ToDouble(tby21.Text);

                x2 = xMapFunc(x2);
                y2 = yMapFunc(y2);

                p2 = new Point((int)x2, (int)(y2));
                gr.DrawLine(new Pen(Color.Blue, 2.0f), p1, p2);

                gr.FillEllipse(Brushes.Black, (int)p1.X - 2, p1.Y - 2, 4, 4);
                gr.FillEllipse(Brushes.Black, (int)p2.X - 2, p2.Y - 2, 4, 4);

                p1 = p2;
            }
        }

        private void drawYellowCurve(object sender, PaintEventArgs e, ConvertValueToPixel xMapFunc, ConvertValueToPixel yMapFunc)
        {
            int i = 0;
            Point p1 = new Point(), p2 = new Point();
            Graphics gr = e.Graphics;
            double x1, y1;

            //yellow curve               
            for (i = 13; i > 0; i--)
            {
                TextBox tbx2 = (Controls.Find("textBoxX2_" + i.ToString(), true)[0] as TextBox);
                TextBox tby22 = (Controls.Find("textBoxY22_" + i.ToString(), true)[0] as TextBox);

                if (tby22.Text == "" || tbx2.Text == "")
                    continue;

                x1 = Convert.ToSingle(tbx2.Text);
                y1 = Convert.ToSingle(tby22.Text);

                x1 = xMapFunc(x1);
                y1 = yMapFunc(y1);

                p1 = new Point((int)x1, (int)(y1));
                break;
            }

            for (int j = i - 1; j > 0; j--)
            {
                TextBox tbx2 = (Controls.Find("textBoxX2_" + j.ToString(), true)[0] as TextBox);
                TextBox tby22 = (Controls.Find("textBoxY22_" + j.ToString(), true)[0] as TextBox);

                if (tby22.Text == "" || tbx2.Text == "")
                    continue;

                double x2 = Convert.ToDouble(tbx2.Text);
                double y2 = Convert.ToDouble(tby22.Text);

                x2 = xMapFunc(x2);
                y2 = yMapFunc(y2);

                p2 = new Point((int)x2, (int)(y2));
                gr.DrawLine(new Pen(Color.Goldenrod, 2.0f), p1, p2);

                gr.FillEllipse(Brushes.Black, (int)p1.X - 2, p1.Y - 2, 4, 4);
                gr.FillEllipse(Brushes.Black, (int)p2.X - 2, p2.Y - 2, 4, 4);

                p1 = p2;
            }
        }

        public double convertXToPixel(double x)
        {
            double graphAreaWidth = paneGraph.Width - 1;
            double xMax = 45;

            double pixelsPerXUnit = graphAreaWidth / xMax;

            return x * pixelsPerXUnit;
        }

        private double convertYAngleSmallBushValueToPixel(double y)
        {
            double graphAreaHeight = paneGraph.Height - 1;
            double yMaxAngleSmollBush = 11;

            double pixelsPerYUnit = graphAreaHeight / yMaxAngleSmollBush;

            y = y * pixelsPerYUnit;

            return graphAreaHeight - y;
        }

        private double convertYAngleLargeBushValueToPixel(double y)
        {
            const double gridHeight = 9;

            double pixelsPerYUnitBetween_0_60 = 8 * gridHeight / (60 - 0);
            double pixelsPerYUnitBetween_60_154 = 5 * gridHeight / 10;
            
            if (y < 60)
            {
                y = y * pixelsPerYUnitBetween_0_60;
            }
            else 
            {
                y = 60.0 * pixelsPerYUnitBetween_0_60 + (y - 60) * pixelsPerYUnitBetween_60_154;
            }
          
            double top = paneGraph.Height - 1;

            return top - y;
        }

        private void graphPanel_Paint(object sender, PaintEventArgs e)  //draw grid and graph
        {
            double gridWidth = 9;
            double gridHeight = 9;

            drawGrid(sender, e, gridWidth, gridHeight);

            ConvertValueToPixel xMapFunc = new ConvertValueToPixel(convertXToPixel);
            ConvertValueToPixel yAngleSmallBushMapFunc = new ConvertValueToPixel(convertYAngleSmallBushValueToPixel);
            ConvertValueToPixel yAngleLargeBushMapFunc = new ConvertValueToPixel(convertYAngleLargeBushValueToPixel);

            drawRedCurve(sender, e, xMapFunc, yAngleSmallBushMapFunc);
            drawGreenCurve(sender, e, xMapFunc, yAngleLargeBushMapFunc);
            drawBlueCurve(sender, e, xMapFunc, yAngleSmallBushMapFunc);
            drawYellowCurve(sender, e, xMapFunc, yAngleLargeBushMapFunc);
        }

        private void textBoxDI_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!(char.IsDigit(e.KeyChar) || e.KeyChar == Convert.ToChar(Keys.Back) || e.KeyChar == '.'))
            {
                e.Handled = true;
            } 
        }

        private void buttonDrawGraph_Click(object sender, EventArgs e)  //Draw Graph button click
        {
            for(int i = 1; i < 16; i++)
            {
                TextBox tbdix1 = (Controls.Find("textBoxDIX1_" + i.ToString(), true)[0] as TextBox);
                TextBox tbx1 = (Controls.Find("textBoxX1_" + i.ToString(), true)[0] as TextBox);
                tbx1.Text = tbdix1.Text;

                TextBox tbdiy11 = (Controls.Find("textBoxDIY11_" + i.ToString(), true)[0] as TextBox);
                TextBox tby11 = (Controls.Find("textBoxY11_" + i.ToString(), true)[0] as TextBox);
                tby11.Text = tbdiy11.Text;

                TextBox tbdiy12 = (Controls.Find("textBoxDIY12_" + i.ToString(), true)[0] as TextBox);
                TextBox tby12 = (Controls.Find("textBoxY12_" + i.ToString(), true)[0] as TextBox);
                tby12.Text = tbdiy12.Text;
            } 

            for(int i = 1; i < 14; i++)
            {
                TextBox tbdix2 = (Controls.Find("textBoxDIX2_" + i.ToString(), true)[0] as TextBox);
                TextBox tbx2 = (Controls.Find("textBoxX2_" + i.ToString(), true)[0] as TextBox);
                tbx2.Text = tbdix2.Text;

                TextBox tbdiy21 = (Controls.Find("textBoxDIY21_" + i.ToString(), true)[0] as TextBox);
                TextBox tby21 = (Controls.Find("textBoxY21_" + i.ToString(), true)[0] as TextBox);
                tby21.Text = tbdiy21.Text;

                TextBox tbdiy22 = (Controls.Find("textBoxDIY22_" + i.ToString(), true)[0] as TextBox);
                TextBox tby22 = (Controls.Find("textBoxY22_" + i.ToString(), true)[0] as TextBox);
                tby22.Text = tbdiy22.Text;                
            }
            paneGraph.Invalidate(true);
        }

        private void button3_Click(object sender, EventArgs e) //new document button click
        {
            DeleteAllData();
            MakeNewDocName();
            SetColorTextBg(Color.FromArgb(224, 224, 224));
        }



        private void MakeNewDocName()
        {
            int doc_id = 0;
            for (int i = 0; i < listBox1.Items.Count; i++)
            {
                string str = listBox1.Items[i].ToString();
                if (str.Contains("document"))
                {
                    string str1 = str.Replace("document", "");
                    int k;
                    try
                    {
                        k = Int32.Parse(str1);
                    }
                    catch
                    {
                        k = 0;
                    }
                    if (k > doc_id)
                    {
                        doc_id = k;
                    }
                }
            }
            if (doc_id == 0) docName = "document1";
            if (doc_id > 0) docName = "document" + (doc_id +1).ToString();

            listBox1.SelectedIndex = -1;
        }
        private void DeleteAllData()
        {
            textBox1.Text = ""; textBox2.Text = "";
            textBox3.Text = ""; textBox4.Text = "";
            textBox5.Text = ""; textBox6.Text = "";
            textBox7.Text = ""; textBox8.Text = "";
            textBox9.Text = ""; textBox10.Text = "";
            textBox11.Text = ""; textBox12.Text = "";
            textBox13.Text = ""; textBox14.Text = "";
            textBox15.Text = ""; textBox16.Text = "";
            textBox17.Text = ""; textBox18.Text = "";
            textBox19.Text = ""; textBox20.Text = "";
            textBox21.Text = ""; textBox22.Text = "";
            textBox23.Text = ""; textBox24.Text = "";
            textBox25.Text = ""; textBox26.Text = "";
            textBox27.Text = ""; textBox100.Text = "";

            checkBox1.Checked = false; checkBox2.Checked = false;
            checkBox3.Checked = false; checkBox4.Checked = false;

            for (int i = 1; i < 16; i++)
            {
                TextBox tbdix1 = (Controls.Find("textBoxDIX1_" + i.ToString(), true)[0] as TextBox);
                TextBox tbx1 = (Controls.Find("textBoxX1_" + i.ToString(), true)[0] as TextBox);
                tbx1.Text = ""; tbdix1.Text = "";

                TextBox tbdiy11 = (Controls.Find("textBoxDIY11_" + i.ToString(), true)[0] as TextBox);
                TextBox tby11 = (Controls.Find("textBoxY11_" + i.ToString(), true)[0] as TextBox);
                tby11.Text = ""; tbdiy11.Text = "";

                TextBox tbdiy12 = (Controls.Find("textBoxDIY12_" + i.ToString(), true)[0] as TextBox);
                TextBox tby12 = (Controls.Find("textBoxY12_" + i.ToString(), true)[0] as TextBox);
                tby12.Text = ""; tbdiy12.Text = "";
            }

            for (int i = 1; i < 14; i++)
            {
                TextBox tbdix2 = (Controls.Find("textBoxDIX2_" + i.ToString(), true)[0] as TextBox);
                TextBox tbx2 = (Controls.Find("textBoxX2_" + i.ToString(), true)[0] as TextBox);
                tbx2.Text = ""; tbdix2.Text = "";

                TextBox tbdiy21 = (Controls.Find("textBoxDIY21_" + i.ToString(), true)[0] as TextBox);
                TextBox tby21 = (Controls.Find("textBoxY21_" + i.ToString(), true)[0] as TextBox);
                tby21.Text = ""; tbdiy21.Text = "";

                TextBox tbdiy22 = (Controls.Find("textBoxDIY22_" + i.ToString(), true)[0] as TextBox);
                TextBox tby22 = (Controls.Find("textBoxY22_" + i.ToString(), true)[0] as TextBox);
                tby22.Text = ""; tbdiy22.Text = "";
            }
            panel1.Invalidate(true);
        }
        public static DialogResult InputBox(string title, string promptText, ref string value)
        {
            Form form = new Form();
            Label label = new Label();
            TextBox textBox = new TextBox();
            Button buttonOk = new Button();
            Button buttonCancel = new Button();

            form.Text = title;
            label.Text = promptText;
            textBox.Text = value;

            buttonOk.Text = "OK";
            buttonCancel.Text = "Cancel";
            buttonOk.DialogResult = DialogResult.OK;
            buttonCancel.DialogResult = DialogResult.Cancel;

            label.SetBounds(9, 20, 372, 13);
            textBox.SetBounds(12, 36, 372, 20);
            buttonOk.SetBounds(228, 72, 75, 23);
            buttonCancel.SetBounds(309, 72, 75, 23);

            label.AutoSize = true;
            textBox.Anchor = textBox.Anchor | AnchorStyles.Right;
            buttonOk.Anchor = AnchorStyles.Bottom | AnchorStyles.Right;
            buttonCancel.Anchor = AnchorStyles.Bottom | AnchorStyles.Right;

            form.ClientSize = new Size(396, 107);
            form.Controls.AddRange(new Control[] { label, textBox, buttonOk, buttonCancel });
            form.ClientSize = new Size(Math.Max(300, label.Right + 10), form.ClientSize.Height);
            form.FormBorderStyle = FormBorderStyle.FixedDialog;
            form.StartPosition = FormStartPosition.CenterScreen;
            form.MinimizeBox = false;
            form.MaximizeBox = false;
            form.AcceptButton = buttonOk;
            form.CancelButton = buttonCancel;

            DialogResult dialogResult = form.ShowDialog();
            value = textBox.Text;
            return dialogResult;
        }

        private void button5_Click(object sender, EventArgs e)  //save document button click
        {            

            if (InputBox("Save document", "document name:", ref docName) == DialogResult.OK)
            {
                if (docName == "")
                {
                    MessageBox.Show("Enter the name of document");
                    return;
                }

                if (listBox1.Items.Contains(docName))
                {
                    DialogResult result = MessageBox.Show("Document '"+docName+"' will be updated. Do you want to continue?", "Note", MessageBoxButtons.YesNo);
                    if (result == DialogResult.No) return;
                    if (result == DialogResult.Yes)
                    {
                        string str = "delete from doctable where doc_name ='" + docName +"'";
                        try
                        {
                            myConnection.Open();
                            OleDbCommand cmd1 = new OleDbCommand(str, myConnection);
                            cmd1.ExecuteNonQuery();
                            myConnection.Close();
                        }
                        catch
                        {

                        }
                        
                    }
                }
                                 
                string c1, c2, c3, c4;
                if (checkBox1.Checked) c1 = "y"; else c1 = "n";
                if (checkBox2.Checked) c2 = "y"; else c2 = "n";
                if (checkBox3.Checked) c3 = "y"; else c3 = "n";
                if (checkBox4.Checked) c4 = "y"; else c4 = "n";

                string d1="", d2="", d3="", d4="", d5="", d6="";

                for(int i = 1; i < 16; i++)
                {
                    TextBox tbx1 = (Controls.Find("textBoxX1_" + i.ToString(), true)[0] as TextBox);
                    d1 += tbx1.Text + ",";

                    TextBox tby11 = (Controls.Find("textBoxY11_" + i.ToString(), true)[0] as TextBox);
                    d2 += tby11.Text + ",";

                    TextBox tby12 = (Controls.Find("textBoxY12_" + i.ToString(), true)[0] as TextBox);
                    d3 += tby12.Text + ",";
                }

                for (int i = 1; i < 14; i++)
                {
                    TextBox tbx2 = (Controls.Find("textBoxX2_" + i.ToString(), true)[0] as TextBox);
                    d4 += tbx2.Text + ",";

                    TextBox tby21 = (Controls.Find("textBoxY21_" + i.ToString(), true)[0] as TextBox);
                    d5 += tby21.Text + ",";

                    TextBox tby22 = (Controls.Find("textBoxY22_" + i.ToString(), true)[0] as TextBox);
                    d6 += tby22.Text + ",";
                }

                string query = @"insert into doctable ([doc_name],[t1],[t2],[t3],[t27],[t100],[t4],[t5],[t6],[t7],[t8],[t9],[t10],[t11],[t12],[t13],[t14],[t15],[t16],[t17],[t18],[t19],[t20],[t21],[t22],[t23],[t24],[t25],[t26],[c1],[c2],[c3],[c4],[d1],[d2],[d3],[d4],[d5],[d6]) 
                     values ('"+docName+"','"+textBox1.Text+"','"+textBox2.Text+"','"+textBox3.Text+"','"+textBox27.Text+"','"+textBox100.Text+ "','"+textBox4.Text+ "','"+ textBox5.Text+ "','"+textBox6.Text +"','"+ textBox7.Text+ "','"+ textBox8.Text+ "','" + textBox9.Text + "','" + textBox10.Text + "','" + textBox11.Text + "','" + textBox12.Text + "','" + textBox13.Text + "','" + textBox14.Text + "','" + textBox15.Text + "','" + textBox16.Text + "','" + textBox17.Text + "','" + textBox18.Text + "','" + textBox19.Text + "','" + textBox20.Text + "','" + textBox21.Text + "','" + textBox22.Text + "','" + textBox23.Text + "','" + textBox24.Text + "','" + textBox25.Text + "','" + textBox26.Text + "','"+c1+ "','" + c2 + "','" + c3 + "','" + c4 + "','"+d1+ "','" + d2 + "','" + d3 + "','" + d4 + "','" + d5 + "','" + d6 + "')";

                try
                {
                    myConnection.Open();
                    OleDbCommand cmd1 = new OleDbCommand(query, myConnection);
                    cmd1.ExecuteNonQuery();
                    myConnection.Close();

                    MessageBox.Show("Document '"+docName+"' has been saved.", "Note");
                    if (!listBox1.Items.Contains(docName))
                    {
                        listBox1.Items.Add(docName);
                    }                   
                    listBox1.SelectedItem = docName;
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.ToString(), "error");
                    return;
                } 
            }
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            lblDocName.Text = docName;
            
            lblFolderPath.Text = System.IO.Path.Combine(pdfPath, docName + ".pdf");
        }

        private void listBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (listBox1.SelectedIndex == -1) return;

            DeleteAllData();

            docName = listBox1.SelectedItem.ToString();
            string query = "select * from doctable where doc_name ='" + docName + "'";
            try
            {
                myConnection.Open();
                OleDbCommand cmd = new OleDbCommand(query, myConnection);
                dr = cmd.ExecuteReader();
                dr.Read();

                textBox1.Text = dr["t1"].ToString();
                textBox2.Text = dr["t2"].ToString();
                textBox3.Text = dr["t3"].ToString();
                textBox4.Text = dr["t4"].ToString();
                textBox5.Text = dr["t5"].ToString();
                textBox6.Text = dr["t6"].ToString();
                textBox7.Text = dr["t7"].ToString();
                textBox8.Text = dr["t8"].ToString();
                textBox9.Text = dr["t9"].ToString();
                textBox10.Text = dr["t10"].ToString();
                textBox11.Text = dr["t11"].ToString();
                textBox12.Text = dr["t12"].ToString();
                textBox13.Text = dr["t13"].ToString();
                textBox14.Text = dr["t14"].ToString();
                textBox15.Text = dr["t15"].ToString();
                textBox16.Text = dr["t16"].ToString();
                textBox17.Text = dr["t17"].ToString();
                textBox18.Text = dr["t18"].ToString();
                textBox19.Text = dr["t19"].ToString();
                textBox20.Text = dr["t20"].ToString();
                textBox21.Text = dr["t21"].ToString();
                textBox22.Text = dr["t22"].ToString();
                textBox23.Text = dr["t23"].ToString();
                textBox24.Text = dr["t24"].ToString();
                textBox25.Text = dr["t25"].ToString();
                textBox26.Text = dr["t26"].ToString();
                textBox27.Text = dr["t27"].ToString();
                textBox100.Text = dr["t100"].ToString();

                if (dr["c1"].ToString() == "y") checkBox1.Checked = true; else checkBox1.Checked = false;
                if (dr["c2"].ToString() == "y") checkBox2.Checked = true; else checkBox2.Checked = false;
                if (dr["c3"].ToString() == "y") checkBox3.Checked = true; else checkBox3.Checked = false;
                if (dr["c4"].ToString() == "y") checkBox4.Checked = true; else checkBox4.Checked = false;

                string[] d1 = new string[15];
                string[] d2 = new string[15];
                string[] d3 = new string[15];
                string[] d4 = new string[15];
                string[] d5 = new string[15];
                string[] d6 = new string[15];

                d1 = dr["d1"].ToString().Split(',');
                d2 = dr["d2"].ToString().Split(',');
                d3 = dr["d3"].ToString().Split(',');
                d4 = dr["d4"].ToString().Split(',');
                d5 = dr["d5"].ToString().Split(',');
                d6 = dr["d6"].ToString().Split(',');

                for(int i = 1; i < 16; i++)
                {
                    TextBox tbdix1 = (Controls.Find("textBoxDIX1_" + i.ToString(), true)[0] as TextBox);
                    TextBox tbx1 = (Controls.Find("textBoxX1_" + i.ToString(), true)[0] as TextBox);

                    System.Console.WriteLine(i.ToString());
                    tbx1.Text = d1[i - 1];
                    tbdix1.Text = d1[i - 1];

                    TextBox tbdiy11 = (Controls.Find("textBoxDIY11_" + i.ToString(), true)[0] as TextBox);
                    TextBox tby11 = (Controls.Find("textBoxY11_" + i.ToString(), true)[0] as TextBox);
                    tby11.Text = d2[i - 1];
                    tbdiy11.Text = d2[i - 1];

                    TextBox tbdiy12 = (Controls.Find("textBoxDIY12_" + i.ToString(), true)[0] as TextBox);
                    TextBox tby12 = (Controls.Find("textBoxY12_" + i.ToString(), true)[0] as TextBox);
                    tby12.Text = d3[i - 1];
                    tbdiy12.Text = d3[i - 1];
                }

                for (int i = 1; i < 14; i++)
                {
                    TextBox tbdix2 = (Controls.Find("textBoxDIX2_" + i.ToString(), true)[0] as TextBox);
                    TextBox tbx2 = (Controls.Find("textBoxX2_" + i.ToString(), true)[0] as TextBox);
                    tbx2.Text = d4[i-1];
                    tbdix2.Text = d4[i-1];

                    TextBox tbdiy21 = (Controls.Find("textBoxDIY21_" + i.ToString(), true)[0] as TextBox);
                    TextBox tby21 = (Controls.Find("textBoxY21_" + i.ToString(), true)[0] as TextBox);
                    tby21.Text = d5[i-1];
                    tbdiy21.Text = d5[i-1];

                    TextBox tbdiy22 = (Controls.Find("textBoxDIY22_" + i.ToString(), true)[0] as TextBox);
                    TextBox tby22 = (Controls.Find("textBoxY22_" + i.ToString(), true)[0] as TextBox);
                    tby22.Text = d6[i-1];
                    tbdiy22.Text = d6[i-1];
                }

                myConnection.Close();
                panel1.Invalidate(true);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.ToString(), "error");
                myConnection.Close();
            }
            
        }

        private void button4_Click(object sender, EventArgs e)  //remove button click
        {
            if (listBox1.SelectedIndex == -1)
            {
                MessageBox.Show("Select the document name in list.", "");
                return;
            }

            DialogResult result = MessageBox.Show("Document '" + docName + "' will be deleted. Do you want to continue?", "Note", MessageBoxButtons.YesNo);
            if (result == DialogResult.No) return;            

                string str = listBox1.SelectedItem.ToString();

            string query = "delete from doctable where doc_name ='" + str + "'";
            try
            {
                myConnection.Open();
                OleDbCommand cmd1 = new OleDbCommand(query, myConnection);
                cmd1.ExecuteNonQuery();
                myConnection.Close();

                listBox1.Items.Remove(str);
                listBox1.SelectedIndex = -1;

                DeleteAllData();
                MakeNewDocName();

                MessageBox.Show("Document '"+ str +"' has been deleted.", "Note");
            }
            catch
            {
                MessageBox.Show("Error: There was a problem with the database.", "error");
            }
        }

        private void button7_Click(object sender, EventArgs e) //select folder button
        {
            FolderBrowserDialog dialog = new FolderBrowserDialog();
            dialog.ShowDialog();
            pdfPath = dialog.SelectedPath; 
        }



        public void SavePDFFile(Image sourceImage, string targetPDFFilePath)

        {
            PdfDocument pdfDocument = null;
            try
            {
                pdfDocument = new PdfDocument();
                double sourceImageWidth = (sourceImage.Width / 96d) * 72d;
                double sourceImageHeight = (sourceImage.Height / 96d) * 72d;
                int pageHeight = (int)((842d / 72d) * 96d);
                int pageCount = (int)Math.Ceiling(sourceImageHeight / 842d);

                for (int i = 0; i < pageCount; i++)
                {
                    Bitmap pageBitmap = new Bitmap(sourceImage.Width, pageHeight);
                    Graphics pageGraphics = Graphics.FromImage(pageBitmap);

                    pageGraphics.DrawImage
                    (
                        sourceImage,
                        new Rectangle(0, 0, sourceImage.Width, pageHeight),
                        new Rectangle(0, i * pageHeight, sourceImage.Width, pageHeight),
                        GraphicsUnit.Pixel
                    );

                    PdfPage pdfPage = new PdfPage();
                    pdfPage.Width = XUnit.FromPoint(sourceImageWidth);
                    pdfDocument.AddPage(pdfPage);
                    XImage xImage = XImage.FromGdiPlusImage(pageBitmap);
                    XGraphics xGraphics = XGraphics.FromPdfPage(pdfPage);
                    xGraphics.DrawImage(xImage, 0, 0);
                }
                pdfDocument.Save(targetPDFFilePath);
            }
            finally
            {
                if (pdfDocument != null)
                {
                    pdfDocument.Clone();
                }
            }
        }

      
    }
}
