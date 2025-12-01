### UI/UX Flow Document

#### Screen List
1. **Dashboard**: Overview of tasks with progress bars.
2. **Task List**: Detailed view of all tasks, sortable by priority/due date.
3. **Add Task**: Form for creating new tasks.
4. **Task Details**: View and edit specific task info.
5. **Collaboration Panel**: Chat-like interface for comments and updates.
6. **Settings**: User preferences and account settings.
7. **Notifications**: In-app notifications center.

#### Layout Descriptions
1. **Dashboard**:
   - Top: Quick action bar with '+ Add Task' button.
   - Middle: RecyclerView showing tasks in card format.
   - Bottom: Progress stats (e.g., completed vs total tasks).
2. **Task List**:
   - Search bar at top for filtering.
   - RecyclerView with task cards, each showing title, due date, priority color.
   - Floating action menu for sorting/grouping options.
3. **Add Task**:
   - Form fields: Title, Description, Due Date, Priority, Tags.
   - Color picker for custom priorities.
   - Save button with validation checks.
4. **Task Details**:
   - Top: Task title and status indicators (completed/in-progress).
   - Middle: Description and history of changes.
   - Bottom: Collaboration panel for comments.
5. **Collaboration Panel**:
   - Chat interface with message thread.
   - Input field for new messages.
   - Mention system for tagging users.
6. **Settings**:
   - User profile section (avatar, name, email).
   - App settings: notifications, sync frequency, themes.
   - Account linking options (Google, Apple).