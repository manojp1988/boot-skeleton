
CREATE TABLE [dbo].[movie](
                              [id] [bigint] IDENTITY(1,1) NOT NULL,
                              [name] [varchar](255) NULL,
                              [release_date] [datetime2](7) NULL,
                              PRIMARY KEY CLUSTERED
                                  (
                                   [id] ASC
                                      )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
