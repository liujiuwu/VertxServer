package wang.gnim.vertx3.ThirdPartyTest.asm;

import org.junit.Test;
import org.skife.jdbi.cglib.asm.ClassWriter;
import org.skife.jdbi.cglib.asm.Opcodes;

/**
 *
 * Created by wanggnim on 2015/8/6.
 */
public class ClassWriterTest {

	@Test
	public void test() {
		ClassWriter cw = new ClassWriter(0);
		// 通过visit方法确定类的头部信息
		cw.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE, "com/asm3/Comparable",
				null, "java/lang/Object", new String[] { "com/asm3/Mesurable" });

        int publicStaticFinal = Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC;
		// 定义类的属性
		cw.visitField(publicStaticFinal, "LESS", "I", null, new Integer(-1)).visitEnd();
		cw.visitField(publicStaticFinal, "EQUAL", "I", null, new Integer(0)).visitEnd();
		cw.visitField(publicStaticFinal, "GREATER", "I", null, new Integer(1)).visitEnd();

		// 定义类的方法
		cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT, "compareTo", "(Ljava/lang/Object;)I", null, null)
				.visitEnd();
		cw.visitEnd(); // 使cw类已经完成

	}
}
