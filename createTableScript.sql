USE [shiftSchedule]
GO

/****** Object:  Table [dbo].[Employees]    Script Date: 2020-10-06 5:57:39 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Employees](
	[Employee_ID] [uniqueidentifier] NOT NULL,
	[Full Name] [nvarchar](50) NOT NULL,
	[Age] [int] NULL,
	[Sex] [nvarchar](1) NULL,
	[Email] [nvarchar](50) NOT NULL,
	[Address] [nchar](50) NULL,
	[Role] [nvarchar](1) NOT NULL,
	[dateEmployed] [datetime] NOT NULL,
	[dateResigned] [datetime] NULL,
	[trainedOpening] [bit] NOT NULL,
	[trainedClosing] [bit] NOT NULL,
 CONSTRAINT [PK_Employees] PRIMARY KEY CLUSTERED 
(
	[Employee_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

USE [shiftSchedule]
GO

/****** Object:  Table [dbo].[Login]    Script Date: 2020-10-06 5:57:46 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Login](
	[Employee_ID] [uniqueidentifier] NOT NULL,
	[password] [nvarchar](50) NOT NULL
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Login]  WITH CHECK ADD  CONSTRAINT [FK_Login_Employees] FOREIGN KEY([Employee_ID])
REFERENCES [dbo].[Employees] ([Employee_ID])
GO

ALTER TABLE [dbo].[Login] CHECK CONSTRAINT [FK_Login_Employees]
GO

USE [shiftSchedule]
GO

/****** Object:  Table [dbo].[Manager]    Script Date: 2020-10-06 5:57:55 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Manager](
	[Employee_ID] [uniqueidentifier] NOT NULL,
	[datePromoted] [datetime] NOT NULL
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Manager]  WITH CHECK ADD  CONSTRAINT [FK_Manager_Employees] FOREIGN KEY([Employee_ID])
REFERENCES [dbo].[Employees] ([Employee_ID])
GO

ALTER TABLE [dbo].[Manager] CHECK CONSTRAINT [FK_Manager_Employees]
GO

USE [shiftSchedule]
GO

USE [shiftSchedule]
GO

/****** Object:  Table [dbo].[Availabilities]    Script Date: 2020-10-06 5:59:00 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Availabilities](
	[Employee_ID] [uniqueidentifier] NOT NULL,
	[Day] [nchar](2) NOT NULL,
	[shiftAvailable] [nvarchar](50) NOT NULL
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Availabilities]  WITH CHECK ADD  CONSTRAINT [FK_Availabilities_Employees] FOREIGN KEY([Employee_ID])
REFERENCES [dbo].[Employees] ([Employee_ID])
GO

ALTER TABLE [dbo].[Availabilities] CHECK CONSTRAINT [FK_Availabilities_Employees]
GO

/****** Object:  Table [dbo].[Shift]    Script Date: 2020-10-06 5:58:18 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Shift](
	[Shift_ID] [uniqueidentifier] NOT NULL,
	[Day] [nchar](2) NOT NULL,
	[Block] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Shift] PRIMARY KEY CLUSTERED 
(
	[Shift_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

USE [shiftSchedule]
GO

/****** Object:  Table [dbo].[Scheduled]    Script Date: 2020-10-06 5:59:23 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Scheduled](
	[Shift_ID] [uniqueidentifier] NOT NULL,
	[Employee_ID] [uniqueidentifier] NOT NULL
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Scheduled]  WITH CHECK ADD  CONSTRAINT [FK_Scheduled_Employees] FOREIGN KEY([Employee_ID])
REFERENCES [dbo].[Employees] ([Employee_ID])
GO

ALTER TABLE [dbo].[Scheduled] CHECK CONSTRAINT [FK_Scheduled_Employees]
GO

ALTER TABLE [dbo].[Scheduled]  WITH CHECK ADD  CONSTRAINT [FK_Scheduled_Shift] FOREIGN KEY([Shift_ID])
REFERENCES [dbo].[Shift] ([Shift_ID])
GO

ALTER TABLE [dbo].[Scheduled] CHECK CONSTRAINT [FK_Scheduled_Shift]
GO




